package com.edutech.gestion_usuario_soporte.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edutech.gestion_usuario_soporte.model.entity.MensajeTicket;
import com.edutech.gestion_usuario_soporte.model.entity.Ticket;
import com.edutech.gestion_usuario_soporte.model.entity.Usuario;
import com.edutech.gestion_usuario_soporte.repository.MensajeTicketRepository;
import com.edutech.gestion_usuario_soporte.repository.TicketRepository;
import com.edutech.gestion_usuario_soporte.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketRepository ticketRepository;
    private final MensajeTicketRepository mensajeRepository;
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/crear/{idUsuario}")
    public ResponseEntity<?> crearTicket(@PathVariable Long idUsuario, @RequestBody Ticket ticket) {
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        if (usuario.isEmpty()) return ResponseEntity.badRequest().body("Usuario no encontrado");

        ticket.setUsuario(usuario.get());
        ticketRepository.save(ticket);
        return ResponseEntity.ok("Ticket creado");
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Ticket>> listarTicketsPorUsuario(@PathVariable Long idUsuario) {
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        if (usuario.isEmpty()) return ResponseEntity.notFound().build();

        List<Ticket> tickets = ticketRepository.findByUsuario(usuario.get());
        return ResponseEntity.ok(tickets);
    }

    @PostMapping("/mensaje/{idTicket}/{idUsuario}")
    public ResponseEntity<?> responderTicket(@PathVariable Long idTicket, @PathVariable Long idUsuario,
                                             @RequestBody MensajeTicket mensaje) {
        Optional<Ticket> ticket = ticketRepository.findById(idTicket);
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);

        if (ticket.isEmpty() || usuario.isEmpty()) {
            return ResponseEntity.badRequest().body("Ticket o usuario no encontrado");
        }

        mensaje.setTicket(ticket.get());
        mensaje.setAutor(usuario.get());
        mensajeRepository.save(mensaje);

        return ResponseEntity.ok("Mensaje enviado");
    }

    @GetMapping("/mensajes/{idTicket}")
    public ResponseEntity<List<MensajeTicket>> obtenerMensajes(@PathVariable Long idTicket) {
        Optional<Ticket> ticket = ticketRepository.findById(idTicket);
        if (ticket.isEmpty()) return ResponseEntity.notFound().build();

        List<MensajeTicket> mensajes = mensajeRepository.findByTicket(ticket.get());
        return ResponseEntity.ok(mensajes);
    }
}