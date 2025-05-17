package com.edutech.gestion_academica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.gestion_academica.model.Pregunta;

@Service
public class PreguntaService {

    @Autowired
    private PreguntaRepository preguntaRepository;

    public Pregunta guardar(Pregunta pregunta) {
        return preguntaRepository.save(pregunta);
    }
}
