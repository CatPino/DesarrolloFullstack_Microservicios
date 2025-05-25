package com.edutech.gestion_academica.service;

import com.edutech.gestion_academica.model.entity.Pregunta;
import com.edutech.gestion_academica.repository.PreguntaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PreguntaService {

    private final PreguntaRepository preguntaRepository;

    public Pregunta crearPregunta(Pregunta pregunta) {
        return preguntaRepository.save(pregunta);
    }

    public List<Pregunta> listarPorEvaluacion(Long idEvaluacion) {
        return preguntaRepository.findByEvaluacionId(idEvaluacion);
    }

    public Optional<Pregunta> obtenerPorId(Long id) {
        return preguntaRepository.findById(id);
    }

    public void eliminarPregunta(Long id) {
        preguntaRepository.deleteById(id);
    }
}
