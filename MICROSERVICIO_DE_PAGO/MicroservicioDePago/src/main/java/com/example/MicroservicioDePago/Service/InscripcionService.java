package com.example.MicroservicioDePago.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.MicroservicioDePago.Model.Entities.Inscripcion;
import com.example.MicroservicioDePago.Repository.InscripcionRepository;

@Service
public class InscripcionService {
    
    @Autowired
    private InscripcionRepository inscripcionRepository;

    public List<Inscripcion> obtenerTodasLasInscripciones() {
        return inscripcionRepository.findAll();
    }

    public Inscripcion obtenerInscripcionPorId(Long idUsuario) {
        //Extraer datos de BD
        Inscripcion inscripcion = inscripcionRepository.findByIdUsuario(idUsuario).isEmpty() ? null : inscripcionRepository.findByIdUsuario(idUsuario).get(0);
        if (inscripcion == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inscripción no encontrada para el usuario con ID: " + idUsuario);
        }
        return inscripcion;
    }

     public Inscripcion registrarInscripcion(long idUsuario, long idCurso, LocalDate fechaInscripcion) {
        try {
            Inscripcion inscripcion = new Inscripcion();
            inscripcion.setIdUsuario(idUsuario);
            inscripcion.setIdCurso(idCurso);
            inscripcion.setFechaInscripcion(fechaInscripcion);
            return inscripcionRepository.save(inscripcion);
        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al registrar la inscripción: " + e.getMessage(), e);
        }
    }
}