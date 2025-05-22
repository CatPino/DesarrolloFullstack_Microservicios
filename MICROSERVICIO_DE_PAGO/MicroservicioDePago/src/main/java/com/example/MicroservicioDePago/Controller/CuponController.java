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

import jakarta.validation.Valid;

@RestController
@RequestMapping("cupon")
public class CuponController {

    @Autowired
    private CuponService scupon; 

    @PostMapping("/crear")
    public String registrarCupon(@Valid @RequestBody CuponRequest CuponRequest) {
        scupon.registrarCupon(CuponRequest);
        return "Cupon agregado";
    }

    @GetMapping("/validar/{codigo}")
    public String validarCupon(@PathVariable String codigo) {
        scupon.validarCupon(codigo);
        return "Cupón válido";
        
}
    @DeleteMapping("/{codigo}")
    public String eliminarCupon(@PathVariable String codigo) {
        scupon.eliminarCuponPorCodigo(codigo);   
        return "Cupón eliminado correctamente";
    }




}



