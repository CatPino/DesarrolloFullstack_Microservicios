package com.edutech.gestion_usuario_soporte;

import com.edutech.gestion_usuario_soporte.model.entity.Rol;
import com.edutech.gestion_usuario_soporte.model.request.RolRequest;
import com.edutech.gestion_usuario_soporte.repository.RolRepository;
import com.edutech.gestion_usuario_soporte.service.RolService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RolServiceTests {

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private RolService rolService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarRoles() {
        // Arrange
        Rol rol1 = new Rol(1L, "ADMIN");
        Rol rol2 = new Rol(2L, "USER");

        when(rolRepository.findAll()).thenReturn(Arrays.asList(rol1, rol2));

        // Act
        List<Rol> resultado = rolService.listar();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(rolRepository, times(1)).findAll();
    }

    @Test
    void testObtenerPorId_Existente() {
        // Arrange
        Long id = 1L;
        Rol rol = new Rol(id, "ADMIN");

        when(rolRepository.findById(id)).thenReturn(Optional.of(rol));

        // Act
        Optional<Rol> resultado = rolService.obtenerPorId(id);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("ADMIN", resultado.get().getNombre());
        verify(rolRepository, times(1)).findById(id);
    }

    @Test
    void testObtenerPorId_NoExistente() {
        // Arrange
        Long id = 1L;
        when(rolRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Rol> resultado = rolService.obtenerPorId(id);

        // Assert
        assertFalse(resultado.isPresent());
        verify(rolRepository, times(1)).findById(id);
    }

    @Test
void testCrearRol_Existente() {
    // Arrange
    RolRequest request = new RolRequest();
    request.setNombre("ADMIN");
    when(rolRepository.existsByNombre("ADMIN")).thenReturn(true);

    // Act
    String resultado = rolService.crear(request);

    // Assert
    assertEquals("Ya existe un rol con ese nombre.", resultado);
    verify(rolRepository, times(1)).existsByNombre("ADMIN");
    verify(rolRepository, never()).save(any());
}

@Test
void testCrearRol_Exitoso() {
    // Arrange
    RolRequest request = new RolRequest();
    request.setNombre("USER");
    when(rolRepository.existsByNombre("USER")).thenReturn(false);

    // Act
    String resultado = rolService.crear(request);

    // Assert
    assertEquals("Rol creado exitosamente", resultado);
    verify(rolRepository, times(1)).existsByNombre("USER");
    verify(rolRepository, times(1)).save(any(Rol.class));
}


    @Test
    void testActualizar_Existente() {
        // Arrange
        Long id = 1L;
        Rol rolExistente = new Rol(id, "ADMIN");
        Rol nuevoRol = new Rol(null, "SUPER_ADMIN");

        when(rolRepository.findById(id)).thenReturn(Optional.of(rolExistente));

        // Act
        String resultado = rolService.actualizar(id, nuevoRol);

        // Assert
        assertEquals("Rol actualizado correctamente", resultado);
        verify(rolRepository, times(1)).findById(id);
        verify(rolRepository, times(1)).save(rolExistente);
        assertEquals("SUPER_ADMIN", rolExistente.getNombre());
    }

    @Test
    void testActualizar_NoExistente() {
        // Arrange
        Long id = 1L;
        Rol nuevoRol = new Rol(null, "SUPER_ADMIN");

        when(rolRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        String resultado = rolService.actualizar(id, nuevoRol);

        // Assert
        assertEquals("Rol no encontrado", resultado);
        verify(rolRepository, times(1)).findById(id);
        verify(rolRepository, never()).save(any());
    }

    @Test
    void testEliminar_Existente() {
        // Arrange
        Long id = 1L;
        when(rolRepository.existsById(id)).thenReturn(true);

        // Act
        String resultado = rolService.eliminar(id);

        // Assert
        assertEquals("Rol eliminado correctamente", resultado);
        verify(rolRepository, times(1)).existsById(id);
        verify(rolRepository, times(1)).deleteById(id);
    }

    @Test
    void testEliminar_NoExistente() {
        // Arrange
        Long id = 1L;
        when(rolRepository.existsById(id)).thenReturn(false);

        // Act
        String resultado = rolService.eliminar(id);

        // Assert
        assertEquals("Rol no encontrado", resultado);
        verify(rolRepository, times(1)).existsById(id);
        verify(rolRepository, never()).deleteById(id);
    }
}
