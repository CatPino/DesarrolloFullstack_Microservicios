package com.example.MicroservicioDePago.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.MicroservicioDePago.Model.Entities.Pago;
import com.example.MicroservicioDePago.Model.Request.PagoRequest;
import com.example.MicroservicioDePago.Repository.PagoRepository;

@Service
public class PagoService {
    
    @Autowired
    private PagoRepository pagoRepository;

    public List<Pago> obtenerTodosLosPagos() {
        return pagoRepository.findAll();
    }

    public List<Pago> obtenerPagosPorUsuarioId(Long idUsuario) {
    List<Pago> pagos = pagoRepository.findPagosByUsuarioId(idUsuario);
    //EXTRAER DATOS DEL USUARIO//

    if (pagos == null || pagos.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontraron pagos para el usuario con ID: " + idUsuario);
    }

    return pagos;
    }

    public Pago guardarPago(PagoRequest pagoRequest) {
        try {
            Pago pago = new Pago();
            pago.setMonto(pagoRequest.getMonto());
            pago.setFechaPago(pagoRequest.getFechaPago());
            pago.setCupon(pagoRequest.getId_cupon());
            pago.setId_inscripcion(pagoRequest.getId_inscripcion());

            return pagoRepository.save(pago);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al registrar el pago: " + e.getMessage(), e);
        }
    }
}

