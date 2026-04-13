package com.barberia.controller;

import com.barberia.domain.Usuario;
import com.barberia.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/usuario") 
public class UsuarioAdminController {

    @Autowired
    private UsuarioService usuarioService;

    // 1.El administrador puede ver una tabla con TODOS los usuarios registrados
    @GetMapping("/listado")
    public String listado(Model model) {
        var usuarios = usuarioService.getUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "admin/usuario/listado";
    }

    // 2. El administrador abre un formulario en blanco para registrar a un nuevo barbero
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "admin/usuario/formulario";
    }

    // 3. Guarda en la base de datos al usuario que el administrador acaba de crear o editar
    @PostMapping("/guardar")
    public String guardar(Usuario usuario, RedirectAttributes redirectAttributes) {
        usuarioService.guardarUsuario(usuario);
        redirectAttributes.addFlashAttribute("mensaje", "Usuario gestionado exitosamente");
        return "redirect:/admin/usuario/listado";
    }

    // 4. El administrador entra a editar los datos de OTRO usuario
    @GetMapping("/modificar/{idUsuario}")
    public String modificar(@PathVariable("idUsuario") Long idUsuario, Model model) {
        Usuario usuario = usuarioService.getUsuarioPorId(idUsuario);
        model.addAttribute("usuario", usuario);
        return "admin/usuario/formulario";
    }

    // 5. El administrador elimina o borra a un usuario del sistema usando su ID
    @GetMapping("/eliminar/{idUsuario}")
    public String eliminar(@PathVariable("idUsuario") Long idUsuario, RedirectAttributes redirectAttributes) {
        usuarioService.eliminarUsuario(idUsuario);
        redirectAttributes.addFlashAttribute("mensaje", "Usuario eliminado exitosamente");
        return "redirect:/admin/usuario/listado";
    }
}