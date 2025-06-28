package com.edutech.gestion_academica.RespuestaServiceTest;

import com.edutech.gestion_academica.model.entity.*;
import com.edutech.gestion_academica.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RespuestaServiceTest {

    @Autowired
    private CursoService cursoService;
    @Autowired
    private EvaluacionService evaluacionService;
    @Autowired
    private PreguntaService preguntaService;
    @Autowired
    private RespuestaService respuestaService;

    private Curso curso;
    private Evaluacion evaluacion;
    private Pregunta pregunta;

    @BeforeEach
    public void setUp() {
        // Buscar o crear curso
        curso = cursoService.listarCursos().stream()
                .filter(c -> "Curso Test".equals(c.getTitulo()))
                .findFirst()
                .orElseGet(() -> {
                    Curso nuevo = new Curso();
                    nuevo.setTitulo("Curso Test");
                    nuevo.setCategoria("Matemática");
                    nuevo.setDescripcion("Curso para prueba de respuesta");
                    nuevo.setDurabilidad(3);
                    nuevo.setPrecio(8000);
                    nuevo.setEstado("ACTIVO");
                    return cursoService.crearCurso(nuevo);
                });

        // Buscar o crear evaluación
        evaluacion = evaluacionService.listarPorCurso(curso.getId()).stream()
                .filter(e -> "Evaluación Rápida".equals(e.getTitulo()))
                .findFirst()
                .orElseGet(() -> {
                    Evaluacion nueva = new Evaluacion();
                    nueva.setTitulo("Evaluación Rápida");
                    nueva.setTipo("Prueba");
                    nueva.setPorcentaje(15.0);
                    nueva.setCurso(curso);
                    return evaluacionService.crearEvaluacion(nueva);
                });

        // Buscar o crear pregunta
        pregunta = preguntaService.listarPorEvaluacion(evaluacion.getId()).stream()
                .filter(p -> "¿Cuánto es 3 + 5?".equals(p.getEnunciado()))
                .findFirst()
                .orElseGet(() -> {
                    Pregunta nueva = new Pregunta();
                    nueva.setEnunciado("¿Cuánto es 3 + 5?");
                    nueva.setPuntaje(2.0);
                    nueva.setEvaluacion(evaluacion);
                    return preguntaService.crearPregunta(nueva);
                });
    }

    @Test
    public void testCrearRespuesta_SinDuplicar() {
        // Buscar si ya existe la respuesta
        Respuesta respuesta = respuestaService.listarPorPregunta(pregunta.getId()).stream()
                .filter(r -> "El resultado es 8".equals(r.getContenido()))
                .findFirst()
                .orElseGet(() -> {
                    Respuesta nueva = new Respuesta();
                    nueva.setContenido("El resultado es 8");
                    nueva.setEsCorrecta(true);
                    nueva.setPregunta(pregunta);
                    return respuestaService.crearRespuesta(nueva);
                });

        // Validaciones
        assertNotNull(respuesta);
        assertNotNull(respuesta.getId());
        assertEquals("El resultado es 8", respuesta.getContenido());
        assertTrue(respuesta.isEsCorrecta());
        assertEquals(pregunta.getId(), respuesta.getPregunta().getId());
    }
}
