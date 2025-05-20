package com.example.MicroservicioDePago.Model.Request;

import java.sql.Date;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CuponRequest {

    @NotBlank
    private String codigo;

    private double descuento;

    private Date fechaExpiracion;

}
