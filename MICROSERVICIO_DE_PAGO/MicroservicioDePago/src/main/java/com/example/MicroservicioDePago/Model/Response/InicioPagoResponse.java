package com.example.MicroservicioDePago.Model.Response;

import lombok.*;

@Data
public class InicioPagoResponse {

    private String mensaje;
    private String UrlPago;
    private Boolean exito;
    
}
