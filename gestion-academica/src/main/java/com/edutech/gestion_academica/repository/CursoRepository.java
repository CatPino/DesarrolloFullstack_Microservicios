package com.edutech.gestion_academica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.gestion_academica.model.entity.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    Curso findByTitulo(String titulo);

}
