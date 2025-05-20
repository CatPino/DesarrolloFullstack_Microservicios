package com.example.MicroservicioDePago.Model.Request;

import java.sql.Date;

import com.example.MicroservicioDePago.Model.Entities.Cupon;
import com.example.MicroservicioDePago.Model.Entities.Inscripcion;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PagoRequest {

    private double monto;

    private Date fechaPago;  

    @NotNull
    private Cupon id_cupon;

    @NotNull
    private Inscripcion id_inscripcion;
}


    
    

