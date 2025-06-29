package com.edutech.gestion_usuario_soporte;

import com.edutech.gestion_usuario_soporte.model.entity.MensajeTicket;
import com.edutech.gestion_usuario_soporte.model.entity.Ticket;
import com.edutech.gestion_usuario_soporte.model.entity.Usuario;
import com.edutech.gestion_usuario_soporte.repository.MensajeTicketRepository;
import com.edutech.gestion_usuario_soporte.repository.TicketRepository;
import com.edutech.gestion_usuario_soporte.repository.UsuarioRepository;
import com.edutech.gestion_usuario_soporte.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TicketServiceTests {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private MensajeTicketRepository mensajeTicketRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearTicket_UsuarioNoEncontrado() {
        // Arrange
        Long idUsuario = 1L;
        Ticket ticket = new Ticket();

        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.empty());

        // Act
        String resultado = ticketService.crearTicket(idUsuario, ticket);

        // Assert
        assertEquals("Usuario no encontrado", resultado);
        verify(usuarioRepository, times(1)).findById(idUsuario);
        verify(ticketRepository, never()).save(any());
    }

    @Test
    void testCrearTicket_Exitoso() {
        // Arrange
        Long idUsuario = 1L;
        Ticket ticket = new Ticket();
        Usuario usuario = new Usuario();
        usuario.setId(idUsuario);

        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(usuario));
        when(ticketRepository.save(ticket)).thenReturn(ticket);

        // Act
        String resultado = ticketService.crearTicket(idUsuario, ticket);

        // Assert
        assertEquals("Ticket creado exitosamente", resultado);
        verify(usuarioRepository, times(1)).findById(idUsuario);
        verify(ticketRepository, times(1)).save(ticket);
    }

    @Test
    void testObtenerTicketsPorUsuario_UsuarioNoEncontrado() {
        // Arrange
        Long idUsuario = 1L;
        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.empty());

        // Act
        List<Ticket> resultado = ticketService.obtenerTicketsPorUsuario(idUsuario);

        // Assert
        assertTrue(resultado.isEmpty());
        verify(usuarioRepository, times(1)).findById(idUsuario);
        verify(ticketRepository, never()).findByUsuario(any());
    }

    @Test
    void testObtenerTicketsPorUsuario_Exitoso() {
        // Arrange
        Long idUsuario = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(idUsuario);

        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();

        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(usuario));
        when(ticketRepository.findByUsuario(usuario)).thenReturn(Arrays.asList(ticket1, ticket2));

        // Act
        List<Ticket> resultado = ticketService.obtenerTicketsPorUsuario(idUsuario);

        // Assert
        assertEquals(2, resultado.size());
        verify(usuarioRepository, times(1)).findById(idUsuario);
        verify(ticketRepository, times(1)).findByUsuario(usuario);
    }

    @Test
    void testAgregarMensaje_TicketOUsuarioNoEncontrado() {
        // Arrange
        Long idTicket = 1L;
        Long idUsuario = 2L;
        MensajeTicket mensaje = new MensajeTicket();

        when(ticketRepository.findById(idTicket)).thenReturn(Optional.empty());
        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.empty());

        // Act
        String resultado = ticketService.agregarMensaje(idTicket, idUsuario, mensaje);

        // Assert
        assertEquals("Ticket o usuario no encontrado", resultado);
        verify(ticketRepository, times(1)).findById(idTicket);
        verify(usuarioRepository, times(1)).findById(idUsuario);
        verify(mensajeTicketRepository, never()).save(any());
    }

    @Test
    void testAgregarMensaje_Exitoso() {
        // Arrange
        Long idTicket = 1L;
        Long idUsuario = 2L;
        MensajeTicket mensaje = new MensajeTicket();

        Ticket ticket = new Ticket();
        ticket.setId(idTicket);

        Usuario usuario = new Usuario();
        usuario.setId(idUsuario);

        when(ticketRepository.findById(idTicket)).thenReturn(Optional.of(ticket));
        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(usuario));
        when(mensajeTicketRepository.save(mensaje)).thenReturn(mensaje);

        // Act
        String resultado = ticketService.agregarMensaje(idTicket, idUsuario, mensaje);

        // Assert
        assertEquals("Mensaje enviado exitosamente", resultado);
        verify(ticketRepository, times(1)).findById(idTicket);
        verify(usuarioRepository, times(1)).findById(idUsuario);
        verify(mensajeTicketRepository, times(1)).save(mensaje);
    }

    @Test
    void testObtenerMensajes_TicketNoEncontrado() {
        // Arrange
        Long idTicket = 1L;
        when(ticketRepository.findById(idTicket)).thenReturn(Optional.empty());

        // Act
        List<MensajeTicket> resultado = ticketService.obtenerMensajes(idTicket);

        // Assert
        assertTrue(resultado.isEmpty());
        verify(ticketRepository, times(1)).findById(idTicket);
        verify(mensajeTicketRepository, never()).findByTicket(any());
    }

    @Test
    void testObtenerMensajes_Exitoso() {
        // Arrange
        Long idTicket = 1L;
        Ticket ticket = new Ticket();
        ticket.setId(idTicket);

        MensajeTicket mensaje1 = new MensajeTicket();
        MensajeTicket mensaje2 = new MensajeTicket();

        when(ticketRepository.findById(idTicket)).thenReturn(Optional.of(ticket));
        when(mensajeTicketRepository.findByTicket(ticket)).thenReturn(Arrays.asList(mensaje1, mensaje2));

        // Act
        List<MensajeTicket> resultado = ticketService.obtenerMensajes(idTicket);

        // Assert
        assertEquals(2, resultado.size());
        verify(ticketRepository, times(1)).findById(idTicket);
        verify(mensajeTicketRepository, times(1)).findByTicket(ticket);
    }
}
