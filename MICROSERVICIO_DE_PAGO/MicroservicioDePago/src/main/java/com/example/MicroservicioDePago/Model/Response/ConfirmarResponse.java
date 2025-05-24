package com.example.MicroservicioDePago.Model.Response;

import lombok.Data;

@Data
public class ConfirmarResponse {
    private String mensaje;
    private Boolean exito;
}
