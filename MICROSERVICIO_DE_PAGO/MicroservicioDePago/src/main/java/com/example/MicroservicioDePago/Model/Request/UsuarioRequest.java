package com.example.MicroservicioDePago.Model.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
public class UsuarioRequest {

    @NotBlank
    private Long id;

    private String primerNombre;

    private String primerApellido;
    
    

    
}
