package com.example.demo.controller;

import com.example.demo.entity.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PerfilController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // Aquí está bien, pero asegúrate que @Autowired no esté causando conflictos
    public PerfilController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/perfil/editar")
    public String mostrarFormularioEdicion(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // Verifica si userDetails es null antes de usarlo
        if (userDetails == null) {
            return "redirect:/login";
        }
        
        try {
            // Buscar el usuario por nombre
            Usuario usuario = usuarioRepository.findByNombre(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            model.addAttribute("usuario", usuario);
            return "editar_perfil";
        } catch (Exception e) {
            // Log del error
            System.err.println("Error al buscar usuario: " + e.getMessage());
            return "redirect:/error";
        }
    }

    @PostMapping("/perfil/editar")
    public String actualizarPerfil(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam String nombre,
            @RequestParam String email,
            @RequestParam(required = false) String password) {

        try {
            Usuario usuario = usuarioRepository.findByNombre(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            usuario.setNombre(nombre);
            usuario.setEmail(email);

            // Si el usuario ingresó una nueva contraseña, encriptarla y actualizarla
            if (password != null && !password.isEmpty()) {
                usuario.setPassword(passwordEncoder.encode(password));
            }

            usuarioRepository.save(usuario);
            return "redirect:/home?success=true";
        } catch (Exception e) {
            System.err.println("Error al actualizar perfil: " + e.getMessage());
            return "redirect:/perfil/editar?error=true";
        }
    }
}