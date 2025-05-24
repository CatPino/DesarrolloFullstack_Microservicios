package com.edutech.gestion_academica.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edutech.gestion_academica.model.entity.Curso;
import com.edutech.gestion_academica.repository.CursoRepository;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoRepository cursoRepository;

    @GetMapping
    public List<Curso> listarCursos() {
        return cursoRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> crearCurso(@RequestBody Curso curso) {
        cursoRepository.save(curso);
        return ResponseEntity.ok("Curso creado");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> obtenerCurso(@PathVariable Long id) {
        return cursoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}