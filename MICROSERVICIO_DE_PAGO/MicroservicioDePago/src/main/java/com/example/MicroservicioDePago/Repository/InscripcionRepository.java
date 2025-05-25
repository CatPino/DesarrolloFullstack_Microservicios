package com.example.MicroservicioDePago.Repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MicroservicioDePago.Model.Entities.Inscripcion;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    Inscripcion findByIdUsuario(Long idUsuario);

    Inscripcion findByIdCurso(Long idCurso);
}



    

