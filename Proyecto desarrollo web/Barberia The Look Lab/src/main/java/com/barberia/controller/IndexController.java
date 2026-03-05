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
    public String cargarPaginaInicio(Model model) {
        // Solo servicios activos
        var servicios = servicioService.getServicios(true);
        model.addAttribute("servicios", servicios);
        return "index";
    }
}
