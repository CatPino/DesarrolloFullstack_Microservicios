package com.example.MicroservicioDePago.Model.Request;

import java.sql.Date;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InscripcionRequest {
    
    @NotBlank
    private String nombre;

    @NotBlank
    private String titulo;

    private Date fechaInscripcion;

    
}
