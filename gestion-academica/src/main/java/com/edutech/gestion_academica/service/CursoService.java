package com.edutech.gestion_academica.service;

import com.edutech.gestion_academica.model.entity.Curso;
import com.edutech.gestion_academica.repository.CursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository cursoRepository;

    public List<Curso> listarCursos() {
        return cursoRepository.findAll();
    }

    public Curso crearCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    public Optional<Curso> obtenerCursoPorId(Long id) {
        return cursoRepository.findById(id);
    }

    public Curso obtenerCursoPorTitulo(String titulo) {
        Curso curso = cursoRepository.findByTitulo(titulo);
        if (curso == null)
            throw new RuntimeException("Curso no encontrado");
        return curso;
    }

    public void eliminarCurso(Long id) {
        cursoRepository.deleteById(id);
    }

}
