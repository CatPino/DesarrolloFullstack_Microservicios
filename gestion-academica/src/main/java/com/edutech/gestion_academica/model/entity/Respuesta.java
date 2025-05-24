package com.edutech.gestion_academica.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "respuestas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contenido;
    private boolean esCorrecta;

    @ManyToOne
    @JoinColumn(name = "pregunta_id")
    @JsonIgnore
    private Pregunta pregunta;
}