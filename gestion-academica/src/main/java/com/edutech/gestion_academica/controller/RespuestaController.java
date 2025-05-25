package com.edutech.gestion_academica.controller;

import com.edutech.gestion_academica.model.entity.Respuesta;
import com.edutech.gestion_academica.service.RespuestaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/respuestas")
@RequiredArgsConstructor
public class RespuestaController {

    private final RespuestaService respuestaService;

    @PostMapping("/crearRespuesta")
    public ResponseEntity<Respuesta> crearRespuesta(@RequestBody Respuesta respuesta) {
        return ResponseEntity.ok(respuestaService.crearRespuesta(respuesta));
    }

    @GetMapping("/pregunta/{preguntaId}")
    public List<Respuesta> listarPorPregunta(@PathVariable Long preguntaId) {
        return respuestaService.listarPorPregunta(preguntaId);
    }

    @GetMapping("/correctas")
    public List<Respuesta> listarRespuestasCorrectas() {
        return respuestaService.listarRespuestasCorrectas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Respuesta> obtenerPorId(@PathVariable Long id) {
        return respuestaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        respuestaService.eliminarRespuesta(id);
        return ResponseEntity.noContent().build();
    }
}
