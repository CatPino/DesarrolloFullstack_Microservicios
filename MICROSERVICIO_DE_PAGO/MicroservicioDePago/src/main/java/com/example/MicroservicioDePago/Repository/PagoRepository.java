package com.example.MicroservicioDePago.Repository;


import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MicroservicioDePago.Model.Entities.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer>  {


    List<Pago> findByIdUsuario(Long idUsuario);

    //List<Pago> findByFechaPagoAfter(Date fecha);

    //List<Pago> findByCupon_Codigo(String codigoCupon);

    //List<Pago> findByPrecio(int precio);
    

    
}
