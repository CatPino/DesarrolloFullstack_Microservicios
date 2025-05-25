package com.edutech.gestion_usuario_soporte.controller;

import com.edutech.gestion_usuario_soporte.model.entity.Usuario;
import com.edutech.gestion_usuario_soporte.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@Valid @RequestBody Usuario usuario) {
        String resultado = usuarioService.registrar(usuario);
        if (resultado.equals("Usuario registrado exitosamente")) {
            return ResponseEntity.ok(Map.of("mensaje", resultado));
        }
        return ResponseEntity.badRequest().body(Map.of("mensaje", resultado));
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        Optional<Usuario> u = usuarioService.login(usuario.getCorreo(), usuario.getContraseña());
        if (u.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("mensaje", "Correo o contraseña incorrecta"));
        }

        Usuario encontrado = u.get();
        return ResponseEntity.ok(Map.of(
                "mensaje", "Login exitoso",
                "usuario", Map.of(
                        "id", encontrado.getId(),
                        "nombre", encontrado.getNombre(),
                        "correo", encontrado.getCorreo(),
                        "rol", encontrado.getRol().getNombre()
                )
        ));
    }

    @GetMapping("/usuarios")
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/usuario/{nombre}")
    public Usuario obtenerNombre(@PathVariable String nombre) {
        return usuarioService.obtenerCursoPorNombre(nombre);
    }

    
}
