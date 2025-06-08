package com.example.MicroservicioDePago.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MicroservicioDePago.Model.Entities.Inscripcion;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    Inscripcion findByNombre(String nombre);

    Inscripcion findByTitulo(String titulo);

    Inscripcion findByNombreAndTitulo(String nombre, String titulo);
}



    

