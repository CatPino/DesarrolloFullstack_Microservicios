package com.example.MicroservicioDePago.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.MicroservicioDePago.Model.Entities.Cupon;
import com.example.MicroservicioDePago.Model.Request.CuponRequest;
import com.example.MicroservicioDePago.Service.CuponService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("cupon")
public class CuponController {

    @Autowired
    private CuponService scupon; 

    @PostMapping("/Crear")
    public Cupon registrarCupon(@Valid @RequestBody CuponRequest cuponRequest) {
        return scupon.registrarCupon(cuponRequest);
    }

      @GetMapping("/validar/{codigo}")
    public Cupon validarCupon(@PathVariable String codigo) {
        try {
            return scupon.validarCupon(codigo);
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(ex.getStatusCode(), ex.getReason());
        }
    }



}



