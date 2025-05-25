package com.example.MicroservicioDePago.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MicroservicioDePago.Model.Entities.Cupon;

@Repository
public interface CuponRepository extends JpaRepository<Cupon, Long> {

   Cupon findByCodigo(String codigo);

   
}
