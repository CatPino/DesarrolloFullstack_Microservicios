package com.example.MicroservicioDePago.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.MicroservicioDePago.Model.Request.CuponRequest;
import com.example.MicroservicioDePago.Service.CuponService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("cupon")
public class CuponController {

    @Autowired
    private CuponService scupon; 

    
    @PostMapping("/crear")
    @Operation(summary = "Registrar nuevo cupón", description = "Permite registrar un nuevo cupón en el sistema a partir de los datos enviados en el cuerpo de la solicitud.")
    public String registrarCupon(@Valid @RequestBody CuponRequest CuponRequest) {
        scupon.registrarCupon(CuponRequest);
        return "Cupon agregado";
    }

    @GetMapping("/validar/{codigo}")
    @Operation(summary = "Validar cupón", description = "Verifica si un código de cupón es válido y está disponible para ser utilizado.")
    public String validarCupon(@PathVariable String codigo) {
        scupon.validarCupon(codigo);
        return "Cupón válido";
        
}
    @DeleteMapping("/{codigo}")
    @Operation(summary = "Eliminar cupón", description = "Elimina un cupón del sistema utilizando su código único.")
    public String eliminarCupon(@PathVariable String codigo) {
        scupon.eliminarCuponPorCodigo(codigo);   
        return "Cupón eliminado correctamente";
    }




}



