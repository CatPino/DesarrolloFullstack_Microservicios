package com.edutech.gestion_usuario_soporte.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.gestion_usuario_soporte.model.Permiso;
import com.edutech.gestion_usuario_soporte.repository.PermisoRepository;

@Service
public class PermisoService {

     @Autowired
    private PermisoRepository permisoRepository;

    public List<Permiso> listar() {
        return permisoRepository.findAll();
    }

    public Permiso guardar(Permiso permiso) {
        return permisoRepository.save(permiso);
    }
    
}
