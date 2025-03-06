package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/home")
public String home(Authentication authentication, Model model) {
    // Pasar el nombre de usuario al modelo
    if (authentication != null) {
        model.addAttribute("username", authentication.getName());
        
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin";
        }
    }
    return "home";
}

    @GetMapping("/perfil")
    public String home() {
        return "perfil";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminPanel(Model model, Authentication authentication) {
        model.addAttribute("usuario", authentication.getName());
        return "admin";
    }
}