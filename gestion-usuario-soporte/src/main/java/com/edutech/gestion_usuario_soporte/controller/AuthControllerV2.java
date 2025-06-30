package com.edutech.gestion_usuario_soporte.controller;

import com.edutech.gestion_usuario_soporte.model.entity.Usuario;
import com.edutech.gestion_usuario_soporte.model.request.LoginRequest;
import com.edutech.gestion_usuario_soporte.model.request.UsuarioRequest;
import com.edutech.gestion_usuario_soporte.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/auth")
@RequiredArgsConstructor
@Tag(name = "Usuarios v2", description = "Controlador versión 2 con Swagger y HATEOAS")
public class AuthControllerV2 {

    private final UsuarioService usuarioService;

    @Operation(summary = "Registrar un nuevo usuario", description = "Permite crear un nuevo usuario en el sistema, asignándole un rol específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Correo ya registrado o rol no existe")
    })
    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody UsuarioRequest usuarioRequest) {
        String resultado = usuarioService.registrar(usuarioRequest);

        if (resultado.equals("Usuario registrado exitosamente")) {
            Link listarUsuariosLink = linkTo(methodOn(AuthControllerV2.class).listarUsuarios()).withRel("todos-los-usuarios");
            return ResponseEntity.ok(EntityModel.of(Map.of("mensaje", resultado), listarUsuariosLink));
        }
        return ResponseEntity.badRequest().body(Map.of("mensaje", resultado));
    }

    @Operation(summary = "Iniciar sesión de usuario", description = "Permite iniciar sesión validando el correo y la contraseña del usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login exitoso"),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<Usuario> u = usuarioService.login(request.getCorreo(), request.getContrasena());

        if (u.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("mensaje", "Correo o contraseña incorrecta"));
        }

        Usuario encontrado = u.get();

        EntityModel<Usuario> resource = EntityModel.of(encontrado,
                linkTo(methodOn(AuthControllerV2.class).login(request)).withSelfRel(),
                linkTo(methodOn(AuthControllerV2.class).listarUsuarios()).withRel("todos-los-usuarios"),
                linkTo(methodOn(AuthControllerV2.class).obtenerNombre(encontrado.getPrimerNombre())).withRel("buscar-por-nombre")
        );

        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Listar todos los usuarios", description = "Devuelve el listado completo de usuarios existentes.")
    @GetMapping("/usuarios")
    public ResponseEntity<?> listarUsuarios() {
        List<EntityModel<Usuario>> usuarios = usuarioService.listarUsuarios().stream()
                .map(usuario -> EntityModel.of(usuario,
                        linkTo(methodOn(AuthControllerV2.class).obtenerNombre(usuario.getPrimerNombre())).withSelfRel()))
                .toList();

        return ResponseEntity.ok(CollectionModel.of(usuarios,
                linkTo(methodOn(AuthControllerV2.class).listarUsuarios()).withSelfRel()));
    }

    @Operation(summary = "Obtener usuario por primer nombre", description = "Busca un usuario específico por su primer nombre.")
    @GetMapping("/usuario/{nombre}")
    public ResponseEntity<?> obtenerNombre(@PathVariable String nombre) {
        try {
            Usuario usuario = usuarioService.obtenerUsuarioPorNombre(nombre);

            EntityModel<Usuario> resource = EntityModel.of(usuario,
                    linkTo(methodOn(AuthControllerV2.class).obtenerNombre(nombre)).withSelfRel(),
                    linkTo(methodOn(AuthControllerV2.class).listarUsuarios()).withRel("todos-los-usuarios"));

            return ResponseEntity.ok(resource);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensaje", e.getMessage()));
        }
    }
}
