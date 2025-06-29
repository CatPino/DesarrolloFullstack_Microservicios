package com.edutech.gestion_academica.controller;

import com.edutech.gestion_academica.model.entity.Pregunta;
import com.edutech.gestion_academica.service.PreguntaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Preguntas", description = "Operaciones para gestionar preguntas de evaluaciones")
@RestController
@RequestMapping("/api/preguntas")
@RequiredArgsConstructor
public class PreguntaController {

    private final PreguntaService preguntaService;

    @Operation(summary = "Crear una nueva pregunta")
    @PostMapping("/crearPregunta")
    public ResponseEntity<Pregunta> crearPregunta(@RequestBody Pregunta pregunta) {
        Pregunta nuevaPregunta = preguntaService.crearPregunta(pregunta);
        return ResponseEntity.ok(nuevaPregunta);
    }

    @Operation(summary = "Listar preguntas por evaluación")
    @GetMapping("/evaluacion/{idEvaluacion}")
    public List<Pregunta> listarPorEvaluacion(@PathVariable Long idEvaluacion) {
        return preguntaService.listarPorEvaluacion(idEvaluacion);
    }

    @Operation(summary = "Obtener pregunta por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Pregunta> obtenerPorId(@PathVariable Long id) {
        return preguntaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar pregunta por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        preguntaService.eliminarPregunta(id);
        return ResponseEntity.noContent().build();
    }
}
