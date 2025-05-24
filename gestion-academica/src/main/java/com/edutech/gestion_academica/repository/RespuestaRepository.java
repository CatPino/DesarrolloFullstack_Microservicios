package com.edutech.gestion_academica.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.gestion_academica.model.entity.Respuesta;

import java.util.List;

@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    List<Respuesta> findByPreguntaId(Long preguntaId);
    List<Respuesta> findByEsCorrectaTrue();
}