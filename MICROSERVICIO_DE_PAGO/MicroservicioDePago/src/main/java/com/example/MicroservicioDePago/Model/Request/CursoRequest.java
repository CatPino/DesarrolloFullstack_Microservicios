package com.example.MicroservicioDePago.Model.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
public class CursoRequest {

    @NotBlank
    private String idCurso;

    private String tituloCurso;

    private int precio;
    
}
