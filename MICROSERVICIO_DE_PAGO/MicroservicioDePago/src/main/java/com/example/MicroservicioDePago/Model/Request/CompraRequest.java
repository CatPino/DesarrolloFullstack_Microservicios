package com.example.MicroservicioDePago.Model.Request;

import lombok.*;

@Data
public class CompraRequest {
    
    private String nombre;    
    private String titulo;       
    private String codigoCupon;
}
