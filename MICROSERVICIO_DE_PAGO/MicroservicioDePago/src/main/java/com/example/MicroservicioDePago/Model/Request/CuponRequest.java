package com.example.MicroservicioDePago.Model.Request;

import java.sql.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CuponRequest {

    @NotBlank
    private String codigo;

    @NotNull
    private double descuento;
    
    @NotNull
    private Date fechaExpiracion;

}
