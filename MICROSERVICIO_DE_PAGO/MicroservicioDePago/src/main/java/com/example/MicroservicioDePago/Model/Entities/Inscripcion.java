package com.example.MicroservicioDePago.Model.Entities;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "inscripciones")
public class Inscripcion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inscripcion")
    private long id_inscripcion;

    @Column(name = "nombre", nullable = false)
    private String nombre;  

    @Column(name = "titulo", nullable = false)
    private String titulo;    

    @Column(name = "fecha_inscripcion", nullable = false)
    private LocalDate fechaInscripcion;
}