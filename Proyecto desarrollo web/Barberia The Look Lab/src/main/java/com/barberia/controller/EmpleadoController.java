package com.barberia.controller;

import com.barberia.domain.Empleado;
import com.barberia.domain.Horario;
import com.barberia.service.EmpleadoService;
import com.barberia.service.HorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/empleado")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private HorarioService horarioService;

    @GetMapping("/listado")
    public String listadoEmpleados(Model model) {
        var empleados = empleadoService.getEmpleados(false);
        model.addAttribute("empleados", empleados);

        model.addAttribute("empleado", new Empleado());

        return "admin/empleado/listado";
    }

    @PostMapping("/guardar")
    public String guardarEmpleado(Empleado empleado, RedirectAttributes redirectAttributes) {
        if (empleado.getIdEmpleado() == null) {
            empleado.setActivo(true);
        }

        empleadoService.save(empleado);
        redirectAttributes.addFlashAttribute("mensaje", "Profesional guardado correctamente.");

        return "redirect:/admin/empleado/listado";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarEmpleado(@PathVariable("id") Long idEmpleado, RedirectAttributes redirectAttributes) {
        Empleado empleado = new Empleado();
        empleado.setIdEmpleado(idEmpleado);

        empleadoService.delete(empleado);

        redirectAttributes.addFlashAttribute("mensaje", "Profesional eliminado del sistema.");
        return "redirect:/admin/empleado/listado";
    }

    @GetMapping("/horario/{id}")
    public String administrarHorarios(@PathVariable("id") Long idEmpleado, Model model) {

        Empleado empleado = new Empleado();
        empleado.setIdEmpleado(idEmpleado);

        empleado = empleadoService.getEmpleado(empleado);

        var horarios = horarioService.getHorariosPorEmpleado(idEmpleado);

        model.addAttribute("empleado", empleado);
        model.addAttribute("horarios", horarios);

        Horario horarioNuevo = new Horario();
        horarioNuevo.setEmpleado(empleado);
        model.addAttribute("horarioNuevo", horarioNuevo);

        return "admin/empleado/horario";
    }

    @PostMapping("/horario/guardar")
    public String guardarHorario(Horario horarioNuevo, RedirectAttributes redirectAttributes) {
        horarioService.save(horarioNuevo);
        redirectAttributes.addFlashAttribute("mensaje", "Horario agregado correctamente.");

        return "redirect:/admin/empleado/horario/" + horarioNuevo.getEmpleado().getIdEmpleado();
    }

    @GetMapping("/horario/eliminar/{idHorario}/{idEmpleado}")
    public String eliminarHorario(@PathVariable("idHorario") Long idHorario, @PathVariable("idEmpleado") Long idEmpleado, RedirectAttributes redirectAttributes) {
        Horario horario = new Horario();
        horario.setIdHorario(idHorario);
        horarioService.delete(horario);

        redirectAttributes.addFlashAttribute("mensaje", "Horario eliminado.");
        return "redirect:/admin/empleado/horario/" + idEmpleado;
    }
}
