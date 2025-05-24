package com.edutech.gestion_usuario_soporte.controller;


import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.edutech.gestion_usuario_soporte.model.entity.Rol;
import com.edutech.gestion_usuario_soporte.model.entity.Usuario;
import com.edutech.gestion_usuario_soporte.repository.RolRepository;
import com.edutech.gestion_usuario_soporte.repository.UsuarioRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            return ResponseEntity.badRequest().body("Correo ya registrado");
        }

        // Asignar rol por defecto (ej: ALUMNO)
        Optional<Rol> rolPorDefecto = rolRepository.findByNombre("ALUMNO");
        if (rolPorDefecto.isEmpty()) {
            return ResponseEntity.badRequest().body("Rol ALUMNO no existe");
        }
        usuario.setRol(rolPorDefecto.get());

        usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
        
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Usuario registrado exitosamente");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        Optional<Usuario> encontrado = usuarioRepository.findByCorreo(usuario.getCorreo());

        if (encontrado.isEmpty()) {
            return ResponseEntity.status(401).body("Usuario no encontrado");
        }

         Usuario u = encontrado.get();

        // Validar la contraseña cifrada usando PasswordEncoder
        if (!passwordEncoder.matches(usuario.getContraseña(), u.getContraseña())) {
            return ResponseEntity.status(401).body("Contraseña incorrecta");
        }

    // Aquí después se puede retornar un JWT
    return ResponseEntity.ok("Login exitoso");
}
}