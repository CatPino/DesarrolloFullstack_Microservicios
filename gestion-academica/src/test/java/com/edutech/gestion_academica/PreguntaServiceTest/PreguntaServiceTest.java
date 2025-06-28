package com.edutech.gestion_academica.PreguntaServiceTest;

import com.edutech.gestion_academica.model.entity.Curso;
import com.edutech.gestion_academica.model.entity.Evaluacion;
import com.edutech.gestion_academica.model.entity.Pregunta;
import com.edutech.gestion_academica.service.CursoService;
import com.edutech.gestion_academica.service.EvaluacionService;
import com.edutech.gestion_academica.service.PreguntaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PreguntaServiceTest {

    @Autowired
    private PreguntaService preguntaService;

    @Autowired
    private CursoService cursoService;

    @Autowired
    private EvaluacionService evaluacionService;

    private Evaluacion evaluacionExistente;

    @BeforeEach
    public void setUp() {
        // Buscar o crear curso
        Curso curso = cursoService.listarCursos().stream()
                .filter(c -> "Curso Test".equals(c.getTitulo()))
                .findFirst()
                .orElseGet(() -> {
                    Curso nuevoCurso = new Curso();
                    nuevoCurso.setTitulo("Curso Test");
                    nuevoCurso.setCategoria("Informática");
                    nuevoCurso.setDescripcion("Curso de prueba para preguntas");
                    nuevoCurso.setDurabilidad(4);
                    nuevoCurso.setPrecio(10000);
                    nuevoCurso.setEstado("ACTIVO");
                    return cursoService.crearCurso(nuevoCurso);
                });

        // Buscar o crear evaluación única
        evaluacionExistente = evaluacionService.listarPorCurso(curso.getId()).stream()
                .filter(e -> "Evaluación Pregunta".equals(e.getTitulo()))
                .findFirst()
                .orElseGet(() -> {
                    Evaluacion nueva = new Evaluacion();
                    nueva.setTitulo("Evaluación Pregunta");
                    nueva.setTipo("Prueba");
                    nueva.setPorcentaje(20.0);
                    nueva.setCurso(curso);
                    return evaluacionService.crearEvaluacion(nueva);
                });
    }

    @Test
    public void testCrearPregunta_DeberiaGuardarPregunta() {
        // Buscar si ya existe una pregunta con el mismo enunciado en esta evaluación
        Pregunta pregunta = preguntaService.listarPorEvaluacion(evaluacionExistente.getId()).stream()
                .filter(p -> "¿Cuánto es 3 + 5?".equals(p.getEnunciado()))
                .findFirst()
                .orElseGet(() -> {
                    // Si no existe, crearla
                    Pregunta nueva = new Pregunta();
                    nueva.setEnunciado("¿Cuánto es 3 + 5?");
                    nueva.setPuntaje(5.0);
                    nueva.setEvaluacion(evaluacionExistente);
                    return preguntaService.crearPregunta(nueva);
                });

        // Validaciones
        assertNotNull(pregunta);
        assertNotNull(pregunta.getId());
        assertEquals("¿Cuánto es 3 + 5?", pregunta.getEnunciado());
        assertEquals(5.0, pregunta.getPuntaje());
        assertEquals(evaluacionExistente.getId(), pregunta.getEvaluacion().getId());
    }

}
