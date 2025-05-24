package com.example.MicroservicioDePago.Model.Request;

import java.sql.Date;

import lombok.Data;

@Data
public class PagoRequest {

    private int precio;

    private Date fechaPago;  

    private String codigoCupon;

    private Long idUsuario;

    private Long idCurso; 

}


    
    

