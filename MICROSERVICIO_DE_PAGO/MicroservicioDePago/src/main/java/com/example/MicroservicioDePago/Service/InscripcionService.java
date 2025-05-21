package com.example.MicroservicioDePago.Service;

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
        Inscripcion inscripcion = inscripcionRepository.findByIdUsuario(idUsuario).isEmpty() ? null : inscripcionRepository.findByIdUsuario(idUsuario).get(0);
        if (inscripcion == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inscripción no encontrada para el usuario con ID: " + idUsuario);
        }
        return inscripcion;
    }

     public Inscripcion registrarInscripcion(InscripcionRequest inscripcionRequest) {
        try {
            Inscripcion inscripcion = new Inscripcion();
            inscripcion.setIdUsuario(inscripcionRequest.getIdUsuario());
            inscripcion.setIdCurso(inscripcionRequest.getIdCurso());
            inscripcion.setFechaInscripcion(inscripcionRequest.getFechaInscripcion());
            return inscripcionRepository.save(inscripcion);
        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al registrar la inscripción: " + e.getMessage(), e);
        }
    }
}