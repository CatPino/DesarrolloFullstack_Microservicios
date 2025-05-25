package com.example.MicroservicioDePago.Repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MicroservicioDePago.Model.Entities.Pago;



@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    Pago findByIdPago(Long idPago);
}