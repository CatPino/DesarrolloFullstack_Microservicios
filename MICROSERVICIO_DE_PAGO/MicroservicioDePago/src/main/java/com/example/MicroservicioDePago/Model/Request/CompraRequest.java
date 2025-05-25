package com.example.MicroservicioDePago.Model.Request;

import lombok.*;

@Data
public class CompraRequest {

    private Long idCurso;       // ID del curso que se está comprando
    private Long idUsuario;     // ID del usuario que realiza la compra
    private String codigoCupon;
}
