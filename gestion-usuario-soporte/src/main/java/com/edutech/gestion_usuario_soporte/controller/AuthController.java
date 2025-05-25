package com.edutech.gestion_usuario_soporte.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.edutech.gestion_usuario_soporte.model.entity.Rol;
import com.edutech.gestion_usuario_soporte.model.entity.Usuario;
import com.edutech.gestion_usuario_soporte.repository.RolRepository;
import com.edutech.gestion_usuario_soporte.repository.UsuarioRepository;

import java.util.List;
import java.util.Map;
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
        // Validaciones 
        if (usuario.getCorreo() == null || usuario.getCorreo().isBlank() ||
            usuario.getContraseña() == null || usuario.getContraseña().isBlank() ||
            usuario.getNombre() == null || usuario.getNombre().isBlank()) {
            return ResponseEntity.badRequest().body("Nombre, correo y contraseña son obligatorios");
        }

        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            return ResponseEntity.badRequest().body("Correo ya registrado");
        }

        // Asignar rol por defecto ALUMNO
        Optional<Rol> rolPorDefecto = rolRepository.findByNombre("ALUMNO");
        if (rolPorDefecto.isEmpty()) {
            return ResponseEntity.badRequest().body("Rol ALUMNO no existe");
        }

        usuario.setRol(rolPorDefecto.get());
        usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));

        usuarioRepository.save(usuario);

        return ResponseEntity.ok(Map.of(
            "mensaje", "Usuario registrado exitosamente",
            "usuarioId", usuario.getId(),
            "correo", usuario.getCorreo()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        // Validaciones 
        if (usuario.getCorreo() == null || usuario.getCorreo().isBlank() ||
            usuario.getContraseña() == null || usuario.getContraseña().isBlank()) {
            return ResponseEntity.badRequest().body("Correo y contraseña son obligatorios");
        }

        Optional<Usuario> encontrado = usuarioRepository.findByCorreo(usuario.getCorreo());

        if (encontrado.isEmpty()) {
            return ResponseEntity.status(401).body("Usuario no encontrado");
        }

        Usuario u = encontrado.get();

        // Validar la contraseña cifrada
        if (!passwordEncoder.matches(usuario.getContraseña(), u.getContraseña())) {
            return ResponseEntity.status(401).body("Contraseña incorrecta");
        }

        return ResponseEntity.ok(Map.of(
            "mensaje", "Login exitoso",
            "usuario", Map.of(
                "id", u.getId(),
                "nombre", u.getNombre(),
                "correo", u.getCorreo(),
                "rol", u.getRol().getNombre()
            )
        ));
    }

    @GetMapping("/usuarios")
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
}
