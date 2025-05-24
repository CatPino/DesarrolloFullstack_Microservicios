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
    private long id_Pago;

     @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "id_curso", nullable = false)
    private Long idCurso;

    @Column(name = "precio", nullable = false)
    private int precio;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fechaPago;  


    @Column(name = "id_cupon", nullable = false)
    private String codigoCupon;

    @Column(name = "id_transaccion_webpay", length = 50)
    private String idTransaccionWebpay; 
}


    
    

