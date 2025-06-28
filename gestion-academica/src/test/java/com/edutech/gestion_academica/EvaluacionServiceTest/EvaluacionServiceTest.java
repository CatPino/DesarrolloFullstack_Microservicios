package com.edutech.gestion_academica.EvaluacionServiceTest;

import com.edutech.gestion_academica.model.entity.Curso;
import com.edutech.gestion_academica.model.entity.Evaluacion;
import com.edutech.gestion_academica.service.CursoService;
import com.edutech.gestion_academica.service.EvaluacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // 🔄 Garantiza que los datos no queden guardados
public class EvaluacionServiceTest {

    @Autowired
    private EvaluacionService evaluacionService;

    @Autowired
    private CursoService cursoService;

    private Curso cursoExistente;

    @BeforeEach
    public void setUp() {
        // Buscar o crear curso de prueba
        cursoExistente = cursoService.listarCursos().stream()
                .filter(c -> "Curso Test".equals(c.getTitulo()))
                .findFirst()
                .orElseGet(() -> {
                    Curso nuevoCurso = new Curso();
                    nuevoCurso.setTitulo("Curso Test");
                    nuevoCurso.setCategoria("Informática");
                    nuevoCurso.setDescripcion("Curso de prueba para evaluación");
                    nuevoCurso.setDurabilidad(4);
                    nuevoCurso.setPrecio(10000);
                    nuevoCurso.setEstado("ACTIVO");
                    return cursoService.crearCurso(nuevoCurso);
                });

        // Eliminar evaluaciones anteriores asociadas al curso
        evaluacionService.listarPorCurso(cursoExistente.getId())
                .forEach(e -> evaluacionService.eliminarEvaluacion(e.getId()));
    }

    @Test
    public void testCrearEvaluacion_DeberiaGuardarEvaluacion() {
        System.out.println(">>> Test ejecutado: testCrearEvaluacion_DeberiaGuardarEvaluacion");

        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setTitulo("Evaluación Unidad 1");
        evaluacion.setTipo("Prueba");
        evaluacion.setPorcentaje(25.0);
        evaluacion.setCurso(cursoExistente);

        Evaluacion creada = evaluacionService.crearEvaluacion(evaluacion);

        assertNotNull(creada);
        assertNotNull(creada.getId());
        assertEquals("Evaluación Unidad 1", creada.getTitulo());
        assertEquals("Prueba", creada.getTipo());
        assertEquals(25.0, creada.getPorcentaje());
        assertEquals(cursoExistente.getId(), creada.getCurso().getId());
    }
}
