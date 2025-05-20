package com.example.MicroservicioDePago.Model.Request;

import java.sql.Date;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InscripcionRequest {
    
    @NotBlank
    private long idUsuario;

    @NotBlank
    private long idCurso;

    private Date fechaInscripcion;

    
}
