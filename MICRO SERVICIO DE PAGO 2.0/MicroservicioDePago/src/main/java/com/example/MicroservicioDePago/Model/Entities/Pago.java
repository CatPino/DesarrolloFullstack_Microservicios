package com.example.MicroservicioDePago.Model.Entities;

import java.sql.Date;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "Pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private long id_Pago;

    @Column(name = "monto", nullable = false)
    private double monto;

    @Column(name = "fecha_pago", nullable = false)
    private Date fechaPago;  

    @ManyToOne
    @JoinColumn(name = "id_cupon")
    private Cupon cupon;

    @ManyToOne
    @JoinColumn(name = "id_inscripcion")
    private Inscripcion id_inscripcion;
}


    
    

