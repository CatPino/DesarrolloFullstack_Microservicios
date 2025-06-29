package com.edutech.gestion_usuario_soporte;

import com.edutech.gestion_usuario_soporte.model.entity.Rol;
import com.edutech.gestion_usuario_soporte.model.entity.Usuario;
import com.edutech.gestion_usuario_soporte.model.request.UsuarioRequest;
import com.edutech.gestion_usuario_soporte.repository.RolRepository;
import com.edutech.gestion_usuario_soporte.repository.UsuarioRepository;
import com.edutech.gestion_usuario_soporte.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServiceTests {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginExitoso() {
        // Arrange
        String correo = "test@example.com";
        String contrasena = "123456";
        String hashedPassword = "hashed123";

        Usuario usuario = new Usuario();
        usuario.setCorreo(correo);
        usuario.setContrasena(hashedPassword);

        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(contrasena, hashedPassword)).thenReturn(true);

        // Act
        Optional<Usuario> resultado = usuarioService.login(correo, contrasena);

        // Assert
        assertTrue(resultado.isPresent());
        verify(usuarioRepository, times(1)).findByCorreo(correo);
        verify(passwordEncoder, times(1)).matches(contrasena, hashedPassword);
    }

    @Test
    void testLoginFallo() {
        // Arrange
        String correo = "test@example.com";
        String contrasena = "wrongpass";
        String hashedPassword = "hashed123";

        Usuario usuario = new Usuario();
        usuario.setCorreo(correo);
        usuario.setContrasena(hashedPassword);

        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(contrasena, hashedPassword)).thenReturn(false);

        // Act
        Optional<Usuario> resultado = usuarioService.login(correo, contrasena);

        // Assert
        assertFalse(resultado.isPresent());
        verify(usuarioRepository, times(1)).findByCorreo(correo);
        verify(passwordEncoder, times(1)).matches(contrasena, hashedPassword);
    }

    @Test
    void testRegistrarUsuario_CorreoExistente() {
        // Arrange
        UsuarioRequest usuarioRequest = new UsuarioRequest();
        usuarioRequest.setCorreo("test@example.com");

        when(usuarioRepository.existsByCorreo(usuarioRequest.getCorreo())).thenReturn(true);

        // Act
        String resultado = usuarioService.registrar(usuarioRequest);

        // Assert
        assertEquals("Correo ya registrado", resultado);
        verify(usuarioRepository, times(1)).existsByCorreo(usuarioRequest.getCorreo());
    }

    @Test
    void testRegistrarUsuario_SinRolAlumno() {
        // Arrange
        UsuarioRequest usuarioRequest = new UsuarioRequest();
        usuarioRequest.setCorreo("nuevo@example.com");

        when(usuarioRepository.existsByCorreo(usuarioRequest.getCorreo())).thenReturn(false);
        when(rolRepository.findByNombre("ALUMNO")).thenReturn(Optional.empty());

        // Act
        String resultado = usuarioService.registrar(usuarioRequest);

        // Assert
        assertEquals("Rol ALUMNO no existe", resultado);
        verify(usuarioRepository, times(1)).existsByCorreo(usuarioRequest.getCorreo());
        verify(rolRepository, times(1)).findByNombre("ALUMNO");
    }

    @Test
    void testRegistrarUsuario_Exitoso() {
        // Arrange
        UsuarioRequest usuarioRequest = new UsuarioRequest();
        usuarioRequest.setCorreo("nuevo@example.com");
        usuarioRequest.setContrasena("123456");
        usuarioRequest.setNombre("Juan");

        Rol rol = new Rol();
        rol.setId(1L);
        rol.setNombre("ALUMNO");

        when(usuarioRepository.existsByCorreo(usuarioRequest.getCorreo())).thenReturn(false);
        when(rolRepository.findByNombre("ALUMNO")).thenReturn(Optional.of(rol));
        when(passwordEncoder.encode(usuarioRequest.getContrasena())).thenReturn("hashedPassword");

        // Act
        String resultado = usuarioService.registrar(usuarioRequest);

        // Assert
        assertEquals("Usuario registrado exitosamente", resultado);
        verify(usuarioRepository, times(1)).existsByCorreo(usuarioRequest.getCorreo());
        verify(rolRepository, times(1)).findByNombre("ALUMNO");
        verify(passwordEncoder, times(1)).encode(usuarioRequest.getContrasena());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void testListarUsuarios() {
        // Arrange
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);

        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);

        List<Usuario> listaUsuarios = Arrays.asList(usuario1, usuario2);
        when(usuarioRepository.findAll()).thenReturn(listaUsuarios);

        // Act
        List<Usuario> resultado = usuarioService.listarUsuarios();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(usuarioRepository, times(1)).findAll();
    }
}
