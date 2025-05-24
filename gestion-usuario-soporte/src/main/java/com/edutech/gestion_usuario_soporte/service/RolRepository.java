package com.edutech.gestion_usuario_soporte.service;


import org.springframework.data.jpa.repository.JpaRepository;

import com.edutech.gestion_usuario_soporte.model.entity.Rol;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);
}