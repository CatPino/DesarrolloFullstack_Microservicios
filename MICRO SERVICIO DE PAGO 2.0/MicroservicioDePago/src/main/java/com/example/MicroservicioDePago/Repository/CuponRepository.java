package com.example.MicroservicioDePago.Repository;

import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MicroservicioDePago.Model.Entities.Cupon;

@Repository
public interface CuponRepository extends JpaRepository<Cupon, Integer> {

    Cupon findByCodigo(String codigo);

    List<Cupon> findAll();

    List<Cupon> findByActivoTrue();

    List<Cupon> findByFechaExpiracionAfter(Date fecha);
}
