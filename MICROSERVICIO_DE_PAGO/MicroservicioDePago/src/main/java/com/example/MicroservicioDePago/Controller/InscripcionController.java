package com.example.MicroservicioDePago.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MicroservicioDePago.Model.Entities.Inscripcion;
import com.example.MicroservicioDePago.Service.InscripcionService;

@RestController
@RequestMapping("inscripcion")
public class InscripcionController {

    @Autowired
    private InscripcionService sInscripcion;

    @GetMapping("/usuario/{nombre}")
    public Inscripcion obtenerInscripcionPorUsuarioId(@PathVariable String nombre) {
        return sInscripcion.obtenerInscripcionPorNombre(nombre);
    }
}

    
    

