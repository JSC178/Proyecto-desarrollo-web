package com.barberia.controller;

import com.barberia.domain.Servicio;
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
@RequestMapping("/admin/servicio") 
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    //lista de servicios en la vista del administrador
    @GetMapping("/listado")
    public String listado(Model model) {

        var servicios = servicioService.getServicios(false);
        model.addAttribute("servicios", servicios);

        return "admin/servicio/listado";
    }

    //  muestra el formulario en blanco para crear un nuevo servicio
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("servicio", new Servicio());
        return "admin/servicio/formulario";
    }

    
    @PostMapping("/guardar")
    public String guardar(Servicio servicio, RedirectAttributes redirectAttributes) {
        servicioService.save(servicio);
        
        redirectAttributes.addFlashAttribute("mensaje", "Servicio guardado exitosamente");
        
        return "redirect:/admin/servicio/listado";
    }

   
    @GetMapping("/modificar/{idServicio}")
    public String modificar(@PathVariable("idServicio") Long idServicio, Model model) {
        
        Servicio servicioBuscado = new Servicio();
        servicioBuscado.setIdServicio(idServicio);
        
        Servicio servicio = servicioService.getServicio(servicioBuscado);
        model.addAttribute("servicio", servicio);
        
        return "admin/servicio/formulario";
    }

    
    @GetMapping("/eliminar/{idServicio}")
    public String eliminar(@PathVariable("idServicio") Long idServicio, RedirectAttributes redirectAttributes) {
        
        Servicio servicio = new Servicio();
        servicio.setIdServicio(idServicio);
        
        servicioService.delete(servicio);
        
        redirectAttributes.addFlashAttribute("mensaje", "Servicio eliminado exitosamente");
        return "redirect:/admin/servicio/listado";
    }
}