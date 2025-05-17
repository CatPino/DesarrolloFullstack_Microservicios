package com.edutech.gestion_academica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.gestion_academica.model.Respuesta;
import com.edutech.gestion_academica.repository.RespuestaRepository;

@Service
public class RespuestaService {

    @Autowired
    private RespuestaRepository respuestaRepository;

    public Respuesta guardar(Respuesta respuesta) {
        return respuestaRepository.save(respuesta);
    }
}