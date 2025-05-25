package com.example.MicroservicioDePago.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.MicroservicioDePago.Model.Entities.Cupon;
import com.example.MicroservicioDePago.Model.Entities.Inscripcion;
import com.example.MicroservicioDePago.Model.Entities.Pago;
import com.example.MicroservicioDePago.Model.Request.PagoRequest;
import com.example.MicroservicioDePago.Repository.CuponRepository;
import com.example.MicroservicioDePago.Repository.InscripcionRepository;
import com.example.MicroservicioDePago.Repository.PagoRepository;

@Service
public class PagoService {
    
    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private CuponRepository cuponRepository;

    @Autowired
    private InscripcionRepository inscripcionRepository;


    public List<Pago> obtenerTodosLosPagos() {
        return pagoRepository.findAll();
    }
    
    public Pago obtenerPagoPorId(Long idPago) {
        Pago pago = pagoRepository.findByIdPago(idPago);  // Llama al método de la instancia
        if(pago == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pago no encontrado");
        }
        return pago;
    }
    
    public Pago guardarNuevo(PagoRequest pagoRequest) {
    Pago nuevoPago = new Pago();

    nuevoPago.setPrecio(pagoRequest.getPrecio());
    nuevoPago.setFechaPago(pagoRequest.getFechaPago());
    nuevoPago.setIdTransaccionWebpay(pagoRequest.getIdTransaccionWebpay());

        if (pagoRequest.getIdCupon() != null) {
                Cupon cupon = cuponRepository.getReferenceById(pagoRequest.getIdCupon());
            nuevoPago.setCupon(cupon);
            }

            // Asignar Inscripción (solo referencia)
        if (pagoRequest.getIdInscripcion() != null) {
                Inscripcion inscripcion = inscripcionRepository.getReferenceById(pagoRequest.getIdInscripcion());
            nuevoPago.setInscripcion(inscripcion);
        }
    

    return pagoRepository.save(nuevoPago);  // No olvides guardar el pago
    }
}
