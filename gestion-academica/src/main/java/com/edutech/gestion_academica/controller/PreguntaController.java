package com.edutech.gestion_academica.controller;

import com.edutech.gestion_academica.model.entity.Pregunta;
import com.edutech.gestion_academica.service.PreguntaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/preguntas")
@RequiredArgsConstructor
public class PreguntaController {

    private final PreguntaService preguntaService;

    @PostMapping("/crearPregunta")
    public ResponseEntity<Pregunta> crearPregunta(@RequestBody Pregunta pregunta) {
        Pregunta nuevaPregunta = preguntaService.crearPregunta(pregunta);
        return ResponseEntity.ok(nuevaPregunta);
    }

    @GetMapping("/evaluacion/{idEvaluacion}")
    public List<Pregunta> listarPorEvaluacion(@PathVariable Long idEvaluacion) {
        return preguntaService.listarPorEvaluacion(idEvaluacion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pregunta> obtenerPorId(@PathVariable Long id) {
        return preguntaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        preguntaService.eliminarPregunta(id);
        return ResponseEntity.noContent().build();
    }
}
