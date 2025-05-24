package com.example.MicroservicioDePago.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.MicroservicioDePago.Model.Entities.Pago;
import com.example.MicroservicioDePago.Repository.PagoRepository;

@Service
public class PagoService {
    
    @Autowired
    private PagoRepository pagoRepository;

    public List<Pago> obtenerTodosLosPagos() {
        return pagoRepository.findAll();
    }

    public List<Pago> obtenerPagoIdUsuario(Long idUsuario) {
    //Extraer datos de BD
    List<Pago> pagos = pagoRepository.findByIdUsuario(idUsuario);
    if (pagos == null || pagos.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontraron pagos para el usuario con ID: " + idUsuario);
    }

    return pagos;
    }

    public Pago guardarPago(Long userId, Long cursoId, int precio, LocalDate fechaPago, String codigoCupon, String idTransaccionWebpay) {
        try {
            Pago pago = new Pago();
            pago.setIdUsuario(userId);
            pago.setIdCurso(cursoId);
            pago.setPrecio(precio);
            pago.setFechaPago(LocalDate.now());
            pago.setCodigoCupon(codigoCupon);
            pago.setIdTransaccionWebpay(idTransaccionWebpay);

            return pagoRepository.save(pago);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al registrar el pago: " + e.getMessage(), e);
        }
    }
}

