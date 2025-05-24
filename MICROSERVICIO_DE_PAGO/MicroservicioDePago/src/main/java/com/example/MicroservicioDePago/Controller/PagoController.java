package com.example.MicroservicioDePago.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MicroservicioDePago.Model.Entities.Pago;
import com.example.MicroservicioDePago.Service.PagoService;

@RestController
@RequestMapping("pago")
public class PagoController {

    @Autowired
    private PagoService sPago;

     @GetMapping("/usuario/{idUsuario}")
        public List<Pago> obtenerPagosPorUsuarioId(@PathVariable Long idUsuario) {
        return sPago.obtenerPagoIdUsuario(idUsuario);
    }
}


    

