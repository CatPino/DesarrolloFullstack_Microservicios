package com.example.MicroservicioDePago.Model.Request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PagoRequest {
    
    private int precio;
    
    private LocalDate fechaPago;

    private String idTransaccionWebpay;

    private Long idInscripcion; // FK a Inscripcion

    private Long idCupon;       // FK a Cupon
}

    
    

