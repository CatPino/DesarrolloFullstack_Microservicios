package com.edutech.gestion_academica.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.gestion_academica.model.Evaluacion;

@Service
public class EvaluacionService {

    @Autowired
    private evaluacionRepository evaluacionRepository;

    public Evaluacion crear(Evaluacion evaluacion) {
        return evaluacionRepository.save(evaluacion);
    }

    public List<Evaluacion> listar() {
        return evaluacionRepository.findAll();
    }
}