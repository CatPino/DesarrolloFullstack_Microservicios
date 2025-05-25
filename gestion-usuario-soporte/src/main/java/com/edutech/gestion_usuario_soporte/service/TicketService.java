package com.edutech.gestion_usuario_soporte.service;

import com.edutech.gestion_usuario_soporte.model.entity.MensajeTicket;
import com.edutech.gestion_usuario_soporte.model.entity.Ticket;
import com.edutech.gestion_usuario_soporte.model.entity.Usuario;
import com.edutech.gestion_usuario_soporte.repository.MensajeTicketRepository;
import com.edutech.gestion_usuario_soporte.repository.TicketRepository;
import com.edutech.gestion_usuario_soporte.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final MensajeTicketRepository mensajeTicketRepository;
    private final UsuarioRepository usuarioRepository;

    public String crearTicket(Long idUsuario, Ticket ticket) {
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        if (usuario.isEmpty()) return "Usuario no encontrado";

        ticket.setUsuario(usuario.get());
        ticketRepository.save(ticket);
        return "Ticket creado exitosamente";
    }

    public List<Ticket> obtenerTicketsPorUsuario(Long idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .map(ticketRepository::findByUsuario)
                .orElse(List.of());
    }

    public String agregarMensaje(Long idTicket, Long idUsuario, MensajeTicket mensaje) {
        Optional<Ticket> ticket = ticketRepository.findById(idTicket);
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);

        if (ticket.isEmpty() || usuario.isEmpty()) {
            return "Ticket o usuario no encontrado";
        }

        mensaje.setTicket(ticket.get());
        mensaje.setAutor(usuario.get());
        mensajeTicketRepository.save(mensaje);
        return "Mensaje enviado exitosamente";
    }

    public List<MensajeTicket> obtenerMensajes(Long idTicket) {
        return ticketRepository.findById(idTicket)
                .map(mensajeTicketRepository::findByTicket)
                .orElse(List.of());
    }
}
