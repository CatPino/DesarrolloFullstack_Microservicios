package com.edutech.gestion_academica.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edutech.gestion_academica.model.entity.Curso;
import com.edutech.gestion_academica.service.CursoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
@Tag(name = "Cursos", description = "Operaciones relacionadas con los cursos")
public class CursoController {

    private final CursoService cursoService;

    @Operation(summary = "Listar todos los cursos")
    @GetMapping
    public List<Curso> listarCursos() {
        return cursoService.listarCursos();
    }

    @Operation(summary = "Crear un nuevo curso")
    @PostMapping("/crearCurso")
    public ResponseEntity<String> crearCurso(@RequestBody Curso curso) {
        cursoService.crearCurso(curso);
        return ResponseEntity.ok("Curso creado con éxito");
    }

    @Operation(summary = "Obtener un curso por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Curso> obtenerCurso(@PathVariable Long id) {
        return cursoService.obtenerCursoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener un curso por título")
    @GetMapping("/titulo/{titulo}")
    public Curso obtenerCursoPorTitulo(@PathVariable String titulo) {
        return cursoService.obtenerCursoPorTitulo(titulo);
    }

    @Operation(summary = "Eliminar un curso por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCurso(@PathVariable Long id) {
        cursoService.eliminarCurso(id);
        return ResponseEntity.noContent().build();
    }
}
