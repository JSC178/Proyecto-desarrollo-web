package com.barberia.controller;

import com.barberia.domain.Cita;
import com.barberia.service.CitaService;
import com.barberia.service.EmpleadoService;
import com.barberia.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        
        return "/cita/NOMBRE DE LA CLASE HTML QUE VAMOS A UTILIZAR"; // Retorna a la vista agendar.html
    }

    // SC-407: Guardar la cita en la base de datos
    @PostMapping("/guardar")
    public String guardarCita(Cita cita) {
        // El objeto 'cita' ya viene con el id_empleado y id_servicio seleccionados
        citaService.save(cita);
        
        // Redirigimos al historial o al index (La Persona 4 manejará el mensaje de confirmación SC-408)
        return "redirect:/";
    }
}