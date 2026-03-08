package com.barberia.controller;

import com.barberia.domain.Cita;
import com.barberia.service.CitaService;
import com.barberia.service.EmpleadoService;
import com.barberia.service.ServicioService;
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

    // SC-402: Mostrar el formulario y cargar la lista de barberos/estilistas
    @GetMapping("/agendar")
    public String mostrarFormularioAgendar(Model model) {
        // Creamos un objeto Cita vacío para que el formulario lo llene
        model.addAttribute("cita", new Cita());
        
        // Cargamos la lista de empleados para el <select> (Historia SC-402)
        var empleados = empleadoService.getEmpleados(true);
        model.addAttribute("empleados", empleados);
        
        // Cargamos los servicios para que el cliente elija qué quiere hacerse
        var servicios = servicioService.getServicios(true);
        model.addAttribute("servicios", servicios);
        
        return "cita/agendar"; // Retorna a la vista agendar.html
    }

    // SC-407: Guardar la cita en la base de datos
    @PostMapping("/guardar")
    public String guardarCita(Cita cita, RedirectAttributes redirectAttributes) {
        // El objeto 'cita' ya viene con el id_empleado y id_servicio seleccionados
        citaService.save(cita);
        
        //Mensaje de confirmacion de cita
        redirectAttributes.addFlashAttribute("mensaje", "La cita fue agendada de forma correcta");
        
        // Redirigimos al historial o al index (La Persona 4 manejará el mensaje de confirmación SC-408)
        return "redirect:/";
    }
    
    //cancelación de la cita
    @GetMapping("/cancelar/{id}")
    public String cancelarCita(@PathVariable("id") Long idCita, RedirectAttributes redirectAttributes){
        Cita cita = new Cita();
        cita.setIdCita(idCita);
        
        citaService.delete(cita);
        redirectAttributes.addFlashAttribute("mensaje", "La cita fue cancelada exitosamente");
        return "redirect:/";
    }
    
    //carga citas
    @GetMapping("/listado")
    public String listadoCitas (Model model){
        var citas = citaService.getCitas();
        model.addAttribute("citas", citas);
        
        return "servicios/listado";
    }
}