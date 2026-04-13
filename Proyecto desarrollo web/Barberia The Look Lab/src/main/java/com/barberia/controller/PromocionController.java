package com.barberia.controller;

import com.barberia.domain.Promocion;
import com.barberia.service.PromocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/promocion")
public class PromocionController {

    @Autowired
    private PromocionService promocionService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var promociones = promocionService.getPromociones(false);
        model.addAttribute("promociones", promociones);
        model.addAttribute("promocion", new Promocion());
        return "admin/promocion/listado";
    }

    @PostMapping("/guardar")
    public String guardar(Promocion promocion, RedirectAttributes redirectAttributes) {
        promocionService.save(promocion);
        redirectAttributes.addFlashAttribute("mensaje", "Promoción actualizada correctamente.");
        return "redirect:/admin/promocion/listado";
    }
}
