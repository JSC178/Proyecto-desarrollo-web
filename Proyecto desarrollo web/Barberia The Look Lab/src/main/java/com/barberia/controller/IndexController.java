package com.barberia.controller;

import com.barberia.service.ServicioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private final ServicioService servicioService;

    public IndexController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @GetMapping("/")
    public String cargarPaginaInicio() {
        return "index";
    }

    @GetMapping("/nosotros")
    public String cargarPaginaNosotros() {
        return "nosotros/nosotros";
    }

    @GetMapping("/servicios")
    public String cargarPaginaServicios(Model model) {
        var servicios = servicioService.getServicios(true);
        model.addAttribute("servicios", servicios);
        return "servicios/servicios"; 
    }

    @GetMapping("/contacto")
    public String cargarPaginaContacto() {
        return "contacto/contacto"; 
    }
}