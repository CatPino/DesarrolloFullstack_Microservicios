package com.edutech.gestion_academica.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edutech.gestion_academica.model.entity.Evaluacion;
import com.edutech.gestion_academica.service.EvaluacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Evaluaciones", description = "Operaciones relacionadas con las evaluaciones de los cursos")
@RestController
@RequestMapping("/api/evaluaciones")
@RequiredArgsConstructor
public class EvaluacionController {

    private final EvaluacionService evaluacionService;

    @Operation(summary = "Crear una nueva evaluación")
    @PostMapping
    public ResponseEntity<String> crearEvaluacion(@RequestBody Evaluacion evaluacion) {
        evaluacionService.crearEvaluacion(evaluacion);
        return ResponseEntity.ok("Evaluación creada");
    }

    @Operation(summary = "Listar evaluaciones por ID de curso")
    @GetMapping("/curso/{idCurso}")
    public List<Evaluacion> listarPorCurso(@PathVariable Long idCurso) {
        return evaluacionService.listarPorCurso(idCurso);
    }

    @Operation(summary = "Eliminar una evaluación por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEvaluacion(@PathVariable Long id) {
        evaluacionService.eliminarEvaluacion(id);
        return ResponseEntity.noContent().build();
    }
}
