package com.edutech.gestion_academica.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edutech.gestion_academica.model.entity.Evaluacion;
import com.edutech.gestion_academica.service.EvaluacionService;

import java.util.List;

@RestController
@RequestMapping("/api/evaluaciones")
@RequiredArgsConstructor
public class EvaluacionController {

    private final EvaluacionService evaluacionService;

    @PostMapping
    public ResponseEntity<String> crearEvaluacion(@RequestBody Evaluacion evaluacion) {
        evaluacionService.crearEvaluacion(evaluacion);
        return ResponseEntity.ok("Evaluación creada");
    }

    @GetMapping("/curso/{idCurso}")
    public List<Evaluacion> listarPorCurso(@PathVariable Long idCurso) {
        return evaluacionService.listarPorCurso(idCurso);
    }
}
