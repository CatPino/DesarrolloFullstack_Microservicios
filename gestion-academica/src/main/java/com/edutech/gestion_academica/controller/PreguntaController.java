package com.edutech.gestion_academica.controller;


import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edutech.gestion_academica.model.entity.Pregunta;
import com.edutech.gestion_academica.repository.PreguntaRepository;

import java.util.List;

@RestController
@RequestMapping("/api/preguntas")
@RequiredArgsConstructor
public class PreguntaController {

    private final PreguntaRepository preguntaRepository;

    @PostMapping
    public ResponseEntity<?> crearPregunta(@RequestBody Pregunta pregunta) {
        preguntaRepository.save(pregunta);
        return ResponseEntity.ok("Pregunta creada");
    }

    @GetMapping("/evaluacion/{idEvaluacion}")
    public List<Pregunta> listarPorEvaluacion(@PathVariable Long idEvaluacion) {
        return preguntaRepository.findByEvaluacionId(idEvaluacion);
    }
}