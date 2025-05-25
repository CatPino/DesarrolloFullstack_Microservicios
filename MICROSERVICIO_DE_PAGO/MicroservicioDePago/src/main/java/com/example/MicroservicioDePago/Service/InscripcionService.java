package com.example.MicroservicioDePago.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.MicroservicioDePago.Model.Entities.Inscripcion;
import com.example.MicroservicioDePago.Model.Request.InscripcionRequest;
import com.example.MicroservicioDePago.Repository.InscripcionRepository;

@Service
public class InscripcionService {
    
    @Autowired
    private InscripcionRepository inscripcionRepository;

    public List<Inscripcion> obtenerTodasLasInscripciones() {
        return inscripcionRepository.findAll();
    }

    public Inscripcion obtenerInscripcionPorId(Long idUsuario) {
        Inscripcion inscripcion = inscripcionRepository.findByIdUsuario(idUsuario);
        if(inscripcion == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"cupon nop encontrado");
        }
        return inscripcion;
    }


     public Inscripcion guardarInscripcion(InscripcionRequest inscripcionRequest) {
        try {
            Inscripcion inscripcion = new Inscripcion();
            inscripcion.setIdUsuario(inscripcionRequest.getIdUsuario());
            inscripcion.setIdCurso(inscripcionRequest.getIdCurso());
            inscripcion.setFechaInscripcion(LocalDate.now());
            return inscripcionRepository.save(inscripcion);
        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al registrar la inscripción: " + e.getMessage(), e);
        }
    }

}