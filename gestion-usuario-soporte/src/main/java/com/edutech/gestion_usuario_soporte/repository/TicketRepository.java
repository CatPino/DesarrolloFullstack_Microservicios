package com.edutech.gestion_usuario_soporte.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edutech.gestion_usuario_soporte.model.entity.Ticket;
import com.edutech.gestion_usuario_soporte.model.entity.Usuario;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByUsuario(Usuario usuario);
}