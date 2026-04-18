package com.barberia.controller;

import com.barberia.domain.Cita;
import com.barberia.domain.Promocion;
import com.barberia.domain.Usuario;
import com.barberia.service.CitaService;
import com.barberia.service.CorreoService;
import com.barberia.service.EmpleadoService;
import com.barberia.service.PromocionService;
import com.barberia.service.ServicioService;
import com.barberia.service.UsuarioService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cita")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CorreoService correoService;

    @Autowired
    private PromocionService promocionService;

    @GetMapping("/agendar")
    public String mostrarFormularioAgendar(Model model, org.springframework.security.core.Authentication auth) {
        model.addAttribute("cita", new Cita());

        if (auth != null && auth.isAuthenticated()) {
            String username = auth.getName();
            Usuario usuarioLogueado = usuarioService.getUsuarioPorUsername(username);

            int citasCalificadas = citaService.contarCitasCalificadas(usuarioLogueado.getIdUsuario());
            model.addAttribute("citasCalificadas", citasCalificadas);

            Promocion promoAplica = promocionService.getPromocionFidelidadSiAplica(usuarioLogueado.getIdUsuario());

            if (promoAplica != null) {
                model.addAttribute("tieneDescuento", true);
                model.addAttribute("mensajeFidelidad", "¡Felicidades " + usuarioLogueado.getNombre() + "! Por tus 3 visitas calificadas, ganaste un " + promoAplica.getPorcentajeDescuento() + "% de descuento en esta cita.");
            } else {
                model.addAttribute("tieneDescuento", false);

                int faltantes = 3 - (citasCalificadas % 3);

                if (faltantes == 0) {
                    faltantes = 3;
                }

                model.addAttribute("citasFaltantes", faltantes);
            }

            System.out.println("DEBUG: Usuario ID: " + usuarioLogueado.getIdUsuario());
            System.out.println("DEBUG: Citas Calificadas: " + citasCalificadas);
            System.out.println("DEBUG: Promo aplica: " + (promoAplica != null ? "SÍ" : "NO"));
        }

        var empleados = empleadoService.getEmpleados(true);
        model.addAttribute("empleados", empleados);

        var servicios = servicioService.getServicios(true);
        model.addAttribute("servicios", servicios);

        return "cita/agendar";
    }

    @PostMapping("/guardar")
    public String guardarCita(Cita cita, RedirectAttributes redirectAttributes,
            org.springframework.security.core.Authentication auth) {

        String username = auth.getName();
        Usuario usuarioLogueado = usuarioService.getUsuarioPorUsername(username);
        cita.setUsuario(usuarioLogueado);
        cita.setEstado("Pendiente");

        boolean ocupado = citaService.existsByEmpleadoAndFechaHora( //revisa si el barbero ya tiene una cita a esa hora
                cita.getEmpleado(),
                cita.getFechaHora()
        );

        if (ocupado) {
            redirectAttributes.addFlashAttribute("mensajeError",
                    "Ese horario ya está ocupado, elige otro.");

            return "redirect:/cita/agendar";
        }

        citaService.save(cita);

        try {
            String asunto = "Confirmación de cita - Barbería";
            String contenido = "<h2>Hola " + usuarioLogueado.getNombre() + "</h2>"
                    + "<p>Su cita fue agendada de forma correcta.</p>"
                    + "<p><b>Fecha y hora:</b> " + cita.getFechaHora() + "</p>"
                    + "<p>Gracias por preferirnos.</p>";

            correoService.enviarCorreoHtml(usuarioLogueado.getCorreo(), asunto, contenido);
        } catch (MessagingException e) {
            System.out.println("Error al enviar correo: " + e.getMessage());
        }

        redirectAttributes.addFlashAttribute("mensaje", "La cita fue agendada de forma correcta");

        return "redirect:/sesionIniciada";
    }

    @GetMapping("/recordatorio/{id}")
    public String enviarRecordatorio(@PathVariable("id") Long idCita, RedirectAttributes redirectAttributes) {
        Cita cita = new Cita();
        cita.setIdCita(idCita);
        cita = citaService.getCita(cita); //trae toda la informacion de la cita 

        try {
            String asunto = "Recordatorio de cita - Barbería";
            String contenido = "<h2>Hola " + cita.getUsuario().getNombre() + "</h2>"
                    + "<p>Le recordamos que tiene una cita pendiente.</p>"
                    + "<p><b>Fecha y hora:</b> " + cita.getFechaHora() + "</p>"
                    + "<p>Le esperamos.</p>";

            correoService.enviarCorreoHtml(cita.getUsuario().getCorreo(), asunto, contenido);
            redirectAttributes.addFlashAttribute("mensaje", "Recordatorio enviado correctamente");
        } catch (MessagingException e) {
            System.out.println("Error al enviar recordatorio: " + e.getMessage());
            redirectAttributes.addFlashAttribute("mensaje", "No se pudo enviar el recordatorio");
        }

        return "redirect:/cita/listado";
    }

    @GetMapping("/calificar/{id}")
    public String mostrarFormularioCalificar(@PathVariable("id") Long idCita, Model model) {
        Cita cita = new Cita();
        cita.setIdCita(idCita);
        cita = citaService.getCita(cita);

        model.addAttribute("cita", cita);
        return "cita/calificar";
    }

    @PostMapping("/guardarCalificacion")
    public String guardarCalificacion(Cita cita, RedirectAttributes redirectAttributes) {
        Cita citaBD = new Cita();
        citaBD.setIdCita(cita.getIdCita());
        citaBD = citaService.getCita(citaBD);

        citaBD.setCalificacion(cita.getCalificacion());
        citaBD.setComentarioCalificacion(cita.getComentarioCalificacion());

        citaBD.setEstado("Finalizada");

        citaService.save(citaBD);

        redirectAttributes.addFlashAttribute("mensaje", "Calificación guardada correctamente");
        return "redirect:/cita/listado";
    }

    @GetMapping("/cancelar/{id}")
    public String cancelarCita(@PathVariable("id") Long idCita, RedirectAttributes redirectAttributes) {

        Cita cita = new Cita();
        cita.setIdCita(idCita);

        cita = citaService.getCita(cita);

        cita.setEstado("Cancelada");

        citaService.save(cita);

        redirectAttributes.addFlashAttribute("mensaje", "La cita fue cancelada exitosamente");

        return "redirect:/cita/listado";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCita(@PathVariable("id") Long idCita, RedirectAttributes redirectAttributes) {

        Cita cita = new Cita();
        cita.setIdCita(idCita);

        citaService.delete(cita);

        redirectAttributes.addFlashAttribute("mensaje", "La cita fue eliminada correctamente");

        return "redirect:/cita/listado";
    }

    @GetMapping("/listado")
    public String listadoCitas(Model model) {
        var citas = citaService.getCitas();
        model.addAttribute("citas", citas);

        return "cita/listado";
    }

    @GetMapping("/editar/{id}")
    public String editarCita(@PathVariable("id") Long idCita, Model model) {

        Cita cita = new Cita();
        cita.setIdCita(idCita);

        cita = citaService.getCita(cita);

        model.addAttribute("cita", cita);

        var empleados = empleadoService.getEmpleados(true);
        model.addAttribute("empleados", empleados);

        var servicios = servicioService.getServicios(true);
        model.addAttribute("servicios", servicios);

        return "cita/agendar";
    }
}
