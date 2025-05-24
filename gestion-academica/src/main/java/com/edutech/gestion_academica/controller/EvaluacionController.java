package com.edutech.gestion_academica.controller;


import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edutech.gestion_academica.model.entity.Evaluacion;
import com.edutech.gestion_academica.repository.EvaluacionRepository;

import java.util.List;

@RestController
@RequestMapping("/api/evaluaciones")
@RequiredArgsConstructor
public class EvaluacionController {

    private final EvaluacionRepository evaluacionRepository;

    @PostMapping
    public ResponseEntity<?> crearEvaluacion(@RequestBody Evaluacion evaluacion) {
        evaluacionRepository.save(evaluacion);
        return ResponseEntity.ok("Evaluación creada");
    }

    @GetMapping("/curso/{idCurso}")
    public List<Evaluacion> listarPorCurso(@PathVariable Long idCurso) {
        return evaluacionRepository.findByCursoId(idCurso);
    }
}