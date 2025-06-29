package com.edutech.gestion_usuario_soporte.controller;

import com.edutech.gestion_usuario_soporte.model.entity.MensajeTicket;
import com.edutech.gestion_usuario_soporte.model.entity.Ticket;
import com.edutech.gestion_usuario_soporte.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/tickets")
@RequiredArgsConstructor
public class TicketControllerV2 {

    private final TicketService ticketService;

    @Operation(summary = "Crear un ticket para un usuario", description = "Permite a un usuario crear un nuevo ticket de soporte, ingresando un asunto y una descripción.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Usuario no encontrado")
    })
    @PostMapping("/crear/{idUsuario}")
    public ResponseEntity<?> crearTicket(@PathVariable Long idUsuario, @RequestBody Ticket ticket) {
        String resultado = ticketService.crearTicket(idUsuario, ticket);
        if (resultado.contains("no encontrado")) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", resultado));
        }
        return ResponseEntity.ok(Map.of("mensaje", resultado));
    }

    @Operation(summary = "Listar todos los tickets de un usuario (con HATEOAS)", description = "Obtiene todos los tickets creados por un usuario específico, incluyendo enlaces HATEOAS para consultar mensajes de cada ticket.")
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> listarTicketsPorUsuario(@PathVariable Long idUsuario) {
        List<Ticket> tickets = ticketService.obtenerTicketsPorUsuario(idUsuario);

        List<EntityModel<Ticket>> ticketModels = tickets.stream()
                .map(ticket -> EntityModel.of(ticket,
                        linkTo(methodOn(TicketControllerV2.class).obtenerMensajes(ticket.getId())).withRel("mensajes"),
                        linkTo(methodOn(TicketControllerV2.class).listarTicketsPorUsuario(idUsuario)).withRel("todos-los-tickets")
                ))
                .toList();

        return ResponseEntity.ok(CollectionModel.of(ticketModels,
                linkTo(methodOn(TicketControllerV2.class).listarTicketsPorUsuario(idUsuario)).withSelfRel()));
    }

    @Operation(summary = "Responder a un ticket agregando un mensaje", description = "Permite a un usuario agregar una respuesta o comentario a un ticket existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mensaje agregado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Ticket o usuario no encontrado")
    })
    @PostMapping("/mensaje/{idTicket}/{idUsuario}")
    public ResponseEntity<?> responderTicket(@PathVariable Long idTicket, @PathVariable Long idUsuario,
                                             @RequestBody MensajeTicket mensaje) {
        String resultado = ticketService.agregarMensaje(idTicket, idUsuario, mensaje);
        if (resultado.contains("no encontrado")) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", resultado));
        }
        return ResponseEntity.ok(Map.of("mensaje", resultado));
    }

    @Operation(summary = "Obtener todos los mensajes de un ticket (con HATEOAS)", description = "Devuelve el historial completo de mensajes relacionados a un ticket específico, incluyendo enlaces HATEOAS.")
    @GetMapping("/mensajes/{idTicket}")
    public ResponseEntity<?> obtenerMensajes(@PathVariable Long idTicket) {
        List<MensajeTicket> mensajes = ticketService.obtenerMensajes(idTicket);

        List<EntityModel<MensajeTicket>> mensajeModels = mensajes.stream()
                .map(msg -> EntityModel.of(msg,
                        linkTo(methodOn(TicketControllerV2.class).obtenerMensajes(idTicket)).withSelfRel()))
                .toList();

        return ResponseEntity.ok(CollectionModel.of(mensajeModels,
                linkTo(methodOn(TicketControllerV2.class).obtenerMensajes(idTicket)).withSelfRel()));
    }
}
