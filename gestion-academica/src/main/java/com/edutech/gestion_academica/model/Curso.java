package com.edutech.gestion_academica.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Curso {

    @Id
    private String idCurso;

    private String tituloCurso;
    private String categoria;
    private String descripcion;
    private String durabilidadCurso;
    private Double precio;
    private String estadoCurso;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    private List<Modulo> modulos;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    private List<Evaluacion> evaluaciones;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    private List<Certificado> certificados;

}
