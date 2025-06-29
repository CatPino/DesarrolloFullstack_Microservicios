package com.edutech.gestion_usuario_soporte.controller;

import com.edutech.gestion_usuario_soporte.model.entity.Rol;
import com.edutech.gestion_usuario_soporte.model.request.RolRequest;
import com.edutech.gestion_usuario_soporte.service.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolController {

    private final RolService rolService;

    @GetMapping
    public ResponseEntity<List<Rol>> listarRoles() {
        return ResponseEntity.ok(rolService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Optional<Rol> rol = rolService.obtenerPorId(id);
        if (rol.isPresent()) {
            return ResponseEntity.ok(rol.get());
        } else {
            return ResponseEntity.status(404).body(Map.of("mensaje", "Rol no encontrado"));
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody RolRequest request) {
        String mensaje = rolService.crear(request);
        if (mensaje.contains("existe")) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", mensaje));
        }
        return ResponseEntity.ok(Map.of("mensaje", mensaje));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Rol nuevoRol) {
        String mensaje = rolService.actualizar(id, nuevoRol);
        if (mensaje.contains("no encontrado")) {
            return ResponseEntity.status(404).body(Map.of("mensaje", mensaje));
        }
        return ResponseEntity.ok(Map.of("mensaje", mensaje));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        String mensaje = rolService.eliminar(id);
        if (mensaje.contains("no encontrado")) {
            return ResponseEntity.status(404).body(Map.of("mensaje", mensaje));
        }
        return ResponseEntity.ok(Map.of("mensaje", mensaje));
    }
}
