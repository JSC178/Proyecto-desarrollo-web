package com.barberia.controller;

import com.barberia.domain.Usuario;
import com.barberia.service.UsuarioService;
import com.barberia.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PerfilController {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private CitaService citaService; 

    
    @GetMapping("/sesionIniciada")
    public String inicio(Authentication authentication) {
        // Al iniciar sesión, redirigimos directamente a la vista del perfil
        return "redirect:/nosotros";
    }

    // edición del perfil
    @GetMapping("/perfil/informacion")
    public String verPerfil(Model model, Authentication authentication) {
        // Obtenemos el username del usuario logueado
        String username = authentication.getName();
        Usuario usuario = usuarioService.getUsuarioPorUsername(username);
        
        model.addAttribute("usuario", usuario);
        return "perfil/informacion"; 
    }
    @GetMapping("/perfil/historial")
    public String verHistorial(Model model, Authentication authentication) {
        //  Obtiene el usuario que tiene la sesión iniciada
        String username = authentication.getName();
        Usuario usuario = usuarioService.getUsuarioPorUsername(username);
        
        //  Trae solo las citas de este usuario 
        var citas = citaService.getCitasPorUsuario(usuario.getIdUsuario());
        
        
        model.addAttribute("citas", citas);
        
        return "/perfil/historial";
    
    }

    //  Guardar los cambios del perfil
    @PostMapping("/perfil/guardar")
    public String guardarCambios(Usuario usuario) {
        
        usuarioService.actualizarPerfil(usuario);
        return "redirect:/perfil/informacion?success";
    }

    //  Eliminar la cuenta
    @PostMapping("/perfil/eliminar")
    public String eliminarCuenta(Usuario usuario) {
        // Se elimina al usuario de la base de datos local
        usuarioService.eliminarUsuario(usuario.getIdUsuario());
        
        SecurityContextHolder.clearContext(); 
        return "redirect:/?accountDeleted";
    }
}