package com.edutech.gestion_usuario_soporte.controller;

import com.edutech.gestion_usuario_soporte.model.entity.Rol;
import com.edutech.gestion_usuario_soporte.model.request.RolRequest;
import com.edutech.gestion_usuario_soporte.service.RolService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/roles")
@RequiredArgsConstructor
public class RolControllerV2 {

    private final RolService rolService;

    @Operation(summary = "Listar todos los roles", description = "Obtiene un listado de todos los roles registrados en el sistema, incluyendo enlaces HATEOAS por cada rol.")
    @GetMapping
    public ResponseEntity<?> listarRoles() {
        List<EntityModel<Rol>> roles = rolService.listar().stream()
                .map(rol -> EntityModel.of(rol,
                        linkTo(methodOn(RolControllerV2.class).obtenerPorId(rol.getId())).withSelfRel()))
                .toList();

        return ResponseEntity.ok(CollectionModel.of(roles,
                linkTo(methodOn(RolControllerV2.class).listarRoles()).withSelfRel()));
    }

    @Operation(summary = "Obtener rol por ID (con HATEOAS)", description = "Obtiene los detalles de un rol específico usando su ID e incluye enlaces HATEOAS para navegación.")
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Optional<Rol> rolOptional = rolService.obtenerPorId(id);
        if (rolOptional.isPresent()) {
            Rol rol = rolOptional.get();
            EntityModel<Rol> resource = EntityModel.of(rol,
                    linkTo(methodOn(RolControllerV2.class).obtenerPorId(id)).withSelfRel(),
                    linkTo(methodOn(RolControllerV2.class).listarRoles()).withRel("todos-los-roles"),
                    linkTo(methodOn(RolControllerV2.class).eliminar(id)).withRel("eliminar-rol"),
                    linkTo(methodOn(RolControllerV2.class).actualizar(id, rol)).withRel("actualizar-rol"));
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.status(404).body(Map.of("mensaje", "Rol no encontrado"));
        }
    }

    @Operation(summary = "Crear un nuevo rol", description = "Permite crear un nuevo rol en el sistema. Solo requiere el campo 'nombre' en el cuerpo de la petición.")
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody RolRequest request) {
        String mensaje = rolService.crear(request);
        if (mensaje.contains("existe")) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", mensaje));
        }
        return ResponseEntity.ok(Map.of("mensaje", mensaje));
    }

    @Operation(summary = "Actualizar un rol existente", description = "Actualiza el nombre de un rol específico usando su ID.")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Rol nuevoRol) {
        String mensaje = rolService.actualizar(id, nuevoRol);
        if (mensaje.contains("no encontrado")) {
            return ResponseEntity.status(404).body(Map.of("mensaje", mensaje));
        }
        return ResponseEntity.ok(Map.of("mensaje", mensaje));
    }

    @Operation(summary = "Eliminar un rol", description = "Elimina un rol específico del sistema utilizando su ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        String mensaje = rolService.eliminar(id);
        if (mensaje.contains("no encontrado")) {
            return ResponseEntity.status(404).body(Map.of("mensaje", mensaje));
        }
        return ResponseEntity.ok(Map.of("mensaje", mensaje));
    }
}
