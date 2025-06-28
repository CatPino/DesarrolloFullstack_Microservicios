package com.example.MicroservicioDePago.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MicroservicioDePago.Model.Entities.Inscripcion;
import com.example.MicroservicioDePago.Service.InscripcionService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("inscripcion")
public class InscripcionController {

    @Autowired
    private InscripcionService sInscripcion;

    @GetMapping("/usuario/{nombre}")
    @Operation(summary = "Obtener inscripción por nombre de usuario",description = "Retorna los datos de inscripción asociados al nombre de usuario proporcionado.")
    public Inscripcion obtenerInscripcionPorNombre(@PathVariable String nombre) {
        return sInscripcion.obtenerInscripcionPorNombre(nombre);
    }

    @DeleteMapping("/usuario/{nombre}")
    @Operation(summary = "Eliminar inscripción por nombre", description = "Elimina una inscripción según el nombre del usuario")
    public String eliminarInscripcionPorNombre(@PathVariable String nombre) {
    sInscripcion.eliminarInscripcionPorNombre(nombre);
    return "Inscripción eliminada correctamente";
    }
}

    
    

