package com.edutech.gestion_usuario_soporte.controller;

import com.edutech.gestion_usuario_soporte.model.entity.Rol;
import com.edutech.gestion_usuario_soporte.repository.RolRepository;

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

    private final RolRepository rolRepository;

    @GetMapping
    public ResponseEntity<List<Rol>> listarRoles() {
        return ResponseEntity.ok(rolRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerRolPorId(@PathVariable Long id) {
        return rolRepository.findById(id)
            .<ResponseEntity<?>>map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(404).body(Map.of("mensaje", "Rol no encontrado")));
}


    @PostMapping
    public ResponseEntity<?> crearRol(@RequestBody Rol rol) {
        if (rol.getNombre() == null || rol.getNombre().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "El nombre del rol es obligatorio"));
        }

        if (rolRepository.existsByNombre(rol.getNombre())) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Ya existe un rol con ese nombre"));
        }

        Rol nuevoRol = rolRepository.save(rol);
        return ResponseEntity.ok(Map.of(
            "mensaje", "Rol creado exitosamente",
            "rol", nuevoRol
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarRol(@PathVariable Long id, @RequestBody Rol nuevoRol) {
        if (nuevoRol.getNombre() == null || nuevoRol.getNombre().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "El nombre del rol es obligatorio"));
        }

        return rolRepository.findById(id).map(rol -> {
            rol.setNombre(nuevoRol.getNombre());
            rolRepository.save(rol);
            return ResponseEntity.ok(Map.of(
                "mensaje", "Rol actualizado exitosamente",
                "rol", rol
            ));
        }).orElse(ResponseEntity.status(404).body(Map.of("mensaje", "Rol no encontrado")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarRol(@PathVariable Long id) {
        if (!rolRepository.existsById(id)) {
            return ResponseEntity.status(404).body(Map.of("mensaje", "Rol no encontrado"));
        }

        rolRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("mensaje", "Rol eliminado exitosamente"));
    }
}
