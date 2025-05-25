package com.example.MicroservicioDePago.Model.Entities;


import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "Pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private long idPago;

    @Column(name = "precio", nullable = false)
    private int precio;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fechaPago;  

    @Column(name = "id_transaccion_webpay", length = 50)
    private String idTransaccionWebpay; 

    @ManyToOne
    @JoinColumn(name = "id_Inscripcion") // nombre de la columna FK en la tabla estudiante
    private Inscripcion inscripcion;

    @ManyToOne
    @JoinColumn(name = "id_cupon") // nombre de la columna FK en la tabla estudiante
    private Cupon Cupon;
}


    
    

