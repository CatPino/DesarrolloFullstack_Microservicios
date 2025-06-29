package com.edutech.gestion_academica.controller;

import com.edutech.gestion_academica.model.entity.Respuesta;
import com.edutech.gestion_academica.service.RespuestaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Respuestas", description = "Operaciones para gestionar respuestas de preguntas")

@RestController
@RequestMapping("/api/respuestas")
@RequiredArgsConstructor
public class RespuestaController {

    private final RespuestaService respuestaService;

    @Operation(summary = "Crear una nueva respuesta")
    @PostMapping("/crearRespuesta")
    public ResponseEntity<Respuesta> crearRespuesta(@RequestBody Respuesta respuesta) {
        return ResponseEntity.ok(respuestaService.crearRespuesta(respuesta));
    }

    @Operation(summary = "Listar respuestas por pregunta")
    @GetMapping("/pregunta/{preguntaId}")
    public List<Respuesta> listarPorPregunta(@PathVariable Long preguntaId) {
        return respuestaService.listarPorPregunta(preguntaId);
    }

    @Operation(summary = "Listar respuestas correctas")
    @GetMapping("/correctas")
    public List<Respuesta> listarRespuestasCorrectas() {
        return respuestaService.listarRespuestasCorrectas();
    }

    @Operation(summary = "Obtener respuesta por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Respuesta> obtenerPorId(@PathVariable Long id) {
        return respuestaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar respuesta por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        respuestaService.eliminarRespuesta(id);
        return ResponseEntity.noContent().build();
    }
}
