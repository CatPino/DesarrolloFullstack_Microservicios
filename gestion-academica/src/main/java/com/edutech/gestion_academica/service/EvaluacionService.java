package com.edutech.gestion_academica.service;

import com.edutech.gestion_academica.model.entity.Evaluacion;
import com.edutech.gestion_academica.repository.EvaluacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluacionService {

    private final EvaluacionRepository evaluacionRepository;

    public Evaluacion crearEvaluacion(Evaluacion evaluacion) {
        return evaluacionRepository.save(evaluacion);
    }

    public List<Evaluacion> listarPorCurso(Long cursoId) {
        return evaluacionRepository.findByCursoId(cursoId);
    }

    // Nuevo método para listar todas las evaluaciones
    public List<Evaluacion> listarEvaluaciones() {
        return evaluacionRepository.findAll();
    }

    // Nuevo método para eliminar una evaluación por ID
    public void eliminarEvaluacion(Long id) {
        evaluacionRepository.deleteById(id);
    }
}
