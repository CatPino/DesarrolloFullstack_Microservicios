package com.edutech.gestion_academica.CursoServiceTest;

import com.edutech.gestion_academica.model.entity.Curso;
import com.edutech.gestion_academica.service.CursoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CursoServiceTest {

    @Autowired
    private CursoService cursoService;

    private Curso cursoExistente;

    @BeforeEach
    public void setUp() {
        // Eliminar cualquier curso duplicado previamente creado
        cursoService.listarCursos().stream()
                .filter(c -> "Curso Test".equals(c.getTitulo()))
                .forEach(c -> cursoService.eliminarCurso(c.getId()));

        // Crear curso base para las pruebas
        Curso nuevoCurso = new Curso();
        nuevoCurso.setTitulo("Curso Test");
        nuevoCurso.setCategoria("Informática");
        nuevoCurso.setDescripcion("Curso de prueba para testing unitario");
        nuevoCurso.setDurabilidad(4);
        nuevoCurso.setPrecio(10000);
        nuevoCurso.setEstado("ACTIVO");

        cursoExistente = cursoService.crearCurso(nuevoCurso);
    }

    @Test
    public void testObtenerCursoPorTitulo_CuandoExiste_DeberiaRetornarCurso() {
        Curso resultado = cursoService.obtenerCursoPorTitulo("Curso Test");

        assertNotNull(resultado);
        assertEquals("Curso Test", resultado.getTitulo());
        assertEquals("Informática", resultado.getCategoria());
        assertEquals("Curso de prueba para testing unitario", resultado.getDescripcion());
        assertEquals(4, resultado.getDurabilidad());
        assertEquals(10000, resultado.getPrecio());
        assertEquals("ACTIVO", resultado.getEstado());
    }

}
