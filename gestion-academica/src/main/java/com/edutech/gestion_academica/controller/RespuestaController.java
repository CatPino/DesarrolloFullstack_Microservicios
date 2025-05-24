package com.edutech.gestion_academica.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edutech.gestion_academica.model.entity.Respuesta;
import com.edutech.gestion_academica.repository.RespuestaRepository;

import java.util.List;

@RestController
@RequestMapping("/api/respuestas")
@RequiredArgsConstructor
public class RespuestaController {

    private final RespuestaRepository respuestaRepository;

    @PostMapping
    public ResponseEntity<?> crearRespuesta(@RequestBody Respuesta respuesta) {
        respuestaRepository.save(respuesta);
        return ResponseEntity.ok("Respuesta creada");
    }

    @GetMapping("/pregunta/{idPregunta}")
    public List<Respuesta> listarPorPregunta(@PathVariable Long idPregunta) {
        return respuestaRepository.findByPreguntaId(idPregunta);
    }
}