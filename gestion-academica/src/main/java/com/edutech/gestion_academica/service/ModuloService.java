package com.edutech.gestion_academica.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.gestion_academica.model.Modulo;
import com.edutech.gestion_academica.repository.ModuloRepository;

@Service
public class ModuloService {

    @Autowired
    private ModuloRepository moduloRepository;

    public List<Modulo> listar() {
        return moduloRepository.findAll();
    }

    public Modulo guardar(Modulo modulo) {
        return moduloRepository.save(modulo);
    }
}
