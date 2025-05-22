package com.example.MicroservicioDePago.Service;


import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.MicroservicioDePago.Model.Entities.Cupon;
import com.example.MicroservicioDePago.Model.Request.CuponRequest;
import com.example.MicroservicioDePago.Repository.CuponRepository;



@Service
public class CuponService {

    @Autowired
    private CuponRepository cuponRepository;

    public List<Cupon> obtenerTodosCupones() {
        return cuponRepository.findAll();
    }

    public Cupon obtenerCuponPorCodigo(String codigo) {
        Cupon cupon = cuponRepository.findByCodigo(codigo);
        if (cupon == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cupón no encontrado");
        }
        return cupon;
    }

     public Cupon registrarCupon(CuponRequest cuponRequest) {
    try {
        Cupon cuponExistente = cuponRepository.findByCodigo(cuponRequest.getCodigo());

        if (cuponExistente != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un cupón con ese código");
        }
        Cupon nuevoCupon = new Cupon();
        nuevoCupon.setCodigo(cuponRequest.getCodigo());
        nuevoCupon.setDescuento(cuponRequest.getDescuento());
        nuevoCupon.setActivo(true);
        nuevoCupon.setFechaExpiracion(cuponRequest.getFechaExpiracion());

        return cuponRepository.save(nuevoCupon);

    } catch (ResponseStatusException e) {
        throw e; 
    } catch (Exception e) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al registrar el cupón", e);
    }
}


    public Cupon validarCupon(String codigo) {
        Cupon cupon = cuponRepository.findByCodigo(codigo);

        if (cupon == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cupón no encontrado");
        }

        if (!cupon.isActivo()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cupón no está activo");
        }

        if (cupon.getFechaExpiracion() != null &&
            cupon.getFechaExpiracion().toLocalDate().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cupón expirado");
        }
        return cupon;
    }

    
    public void eliminarCuponPorCodigo(String codigo) {
        Cupon cupon = cuponRepository.findByCodigo(codigo);
        
        if (cupon == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cupón no encontrado");
        }
        
        cuponRepository.delete(cupon);  
    }

  
}
