package com.barberia.controller;

import com.barberia.domain.Usuario;
import com.barberia.service.ServicioService;
import com.barberia.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {

    private final ServicioService servicioService;

    @Autowired
    private UsuarioService usuarioService;

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

    @GetMapping("/login")
    public String cargarPaginaLogin() {
        return "login/login";
    }

    @GetMapping("/registro/nuevo")
    public String formularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro/registro"; 
    }

    @PostMapping("/registro/crear")
    public String crearUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.guardarUsuario(usuario);
        return "redirect:/login"; 
    }
    
    @GetMapping("/sesionIniciada")
    public String cargarPaginaSesionIniciada() {
        return "sesionIniciada/sesioniniciada"; 
    }
}