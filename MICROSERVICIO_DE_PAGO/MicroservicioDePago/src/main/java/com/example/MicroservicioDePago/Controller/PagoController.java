package com.example.MicroservicioDePago.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MicroservicioDePago.Model.Entities.Pago;
import com.example.MicroservicioDePago.Service.PagoService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("pago")
public class PagoController {

    @Autowired
    private PagoService sPago;

    @GetMapping("/{idPago}")
    @Operation(summary = "Obtener pago por ID",description = "Retorna la información de un pago específico según su ID.")
    public Pago obtenerPagoPorId(@PathVariable Long idPago) {
        return sPago.obtenerPagoPorId(idPago); 
    }
    
    @DeleteMapping("/{idPago}")
    @Operation(summary = "Eliminar pago por id", description = "Elimina un pago según el id del pago")
    public String eliminarPagoPorId(@PathVariable Long idPago) {
        sPago.eliminarPagoPorId(idPago);
        return "Pago eliminado correctamente";
    }
}




    

