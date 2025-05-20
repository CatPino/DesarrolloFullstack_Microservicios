package com.example.MicroservicioDePago.Repository;


import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.MicroservicioDePago.Model.Entities.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer>  {

    @Query("SELECT p FROM Pago p WHERE p.id_inscripcion.idUsuario = :idUsuario")
    List<Pago> findPagosByUsuarioId(@Param("idUsuario") Long idUsuario);


    List<Pago> findByFechaPagoAfter(Date fecha);

    List<Pago> findByCupon_Codigo(String codigo);

    List<Pago> findByMonto(int monto);
    

    
}
