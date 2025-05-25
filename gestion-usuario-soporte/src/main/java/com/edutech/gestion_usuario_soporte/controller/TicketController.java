package com.edutech.gestion_usuario_soporte.controller;

import com.edutech.gestion_usuario_soporte.model.entity.MensajeTicket;
import com.edutech.gestion_usuario_soporte.model.entity.Ticket;
import com.edutech.gestion_usuario_soporte.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/crear/{idUsuario}")
    public ResponseEntity<?> crearTicket(@PathVariable Long idUsuario, @RequestBody Ticket ticket) {
        String resultado = ticketService.crearTicket(idUsuario, ticket);
        if (resultado.contains("no encontrado")) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", resultado));
        }
        return ResponseEntity.ok(Map.of("mensaje", resultado));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Ticket>> listarTicketsPorUsuario(@PathVariable Long idUsuario) {
        List<Ticket> tickets = ticketService.obtenerTicketsPorUsuario(idUsuario);
        return ResponseEntity.ok(tickets);
    }

    @PostMapping("/mensaje/{idTicket}/{idUsuario}")
    public ResponseEntity<?> responderTicket(@PathVariable Long idTicket, @PathVariable Long idUsuario,
                                             @RequestBody MensajeTicket mensaje) {
        String resultado = ticketService.agregarMensaje(idTicket, idUsuario, mensaje);
        if (resultado.contains("no encontrado")) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", resultado));
        }
        return ResponseEntity.ok(Map.of("mensaje", resultado));
    }

    @GetMapping("/mensajes/{idTicket}")
    public ResponseEntity<List<MensajeTicket>> obtenerMensajes(@PathVariable Long idTicket) {
        List<MensajeTicket> mensajes = ticketService.obtenerMensajes(idTicket);
        return ResponseEntity.ok(mensajes);
    }
}
