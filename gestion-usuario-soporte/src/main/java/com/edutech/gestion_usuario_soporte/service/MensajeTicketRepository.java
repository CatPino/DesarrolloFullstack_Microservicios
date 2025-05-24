package com.edutech.gestion_usuario_soporte.service;


import org.springframework.data.jpa.repository.JpaRepository;

import com.edutech.gestion_usuario_soporte.model.entity.MensajeTicket;
import com.edutech.gestion_usuario_soporte.model.entity.Ticket;

import java.util.List;

public interface MensajeTicketRepository extends JpaRepository<MensajeTicket, Long> {
    List<MensajeTicket> findByTicket(Ticket ticket);
}