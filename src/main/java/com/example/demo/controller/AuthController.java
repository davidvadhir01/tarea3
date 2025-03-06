package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.Reg.Regis;

@Controller
public class AuthController {

    // Redirige la página principal al login
    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }
    
    // Muestra la página de login
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    // Muestra la página de registro y añade el modelo necesario
    @GetMapping("/registro")
    public String registro(Model model) {
        model.addAttribute("usuario", new Regis());
        return "registro";
    }
    
    
    // Muestra la página principal después del login
    @GetMapping("/dashboard")
    public String dashboard() {
        return "home";
    }
}