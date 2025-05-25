package com.edutech.gestion_academica.service;

import com.edutech.gestion_academica.model.entity.Respuesta;
import com.edutech.gestion_academica.repository.RespuestaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RespuestaService {

    private final RespuestaRepository respuestaRepository;

    public Respuesta crearRespuesta(Respuesta respuesta) {
        return respuestaRepository.save(respuesta);
    }

    public List<Respuesta> listarPorPregunta(Long preguntaId) {
        return respuestaRepository.findByPreguntaId(preguntaId);
    }

    public List<Respuesta> listarRespuestasCorrectas() {
        return respuestaRepository.findByEsCorrectaTrue();
    }

    public Optional<Respuesta> obtenerPorId(Long id) {
        return respuestaRepository.findById(id);
    }

    public void eliminarRespuesta(Long id) {
        respuestaRepository.deleteById(id);
    }
}
