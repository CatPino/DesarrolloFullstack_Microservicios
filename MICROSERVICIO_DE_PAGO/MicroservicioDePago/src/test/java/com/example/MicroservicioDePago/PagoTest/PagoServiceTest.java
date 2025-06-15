package com.example.MicroservicioDePago.PagoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.MicroservicioDePago.Model.Entities.Cupon;
import com.example.MicroservicioDePago.Model.Entities.Inscripcion;
import com.example.MicroservicioDePago.Model.Entities.Pago;
import com.example.MicroservicioDePago.Model.Request.CuponRequest;
import com.example.MicroservicioDePago.Model.Request.InscripcionRequest;
import com.example.MicroservicioDePago.Model.Request.PagoRequest;
import com.example.MicroservicioDePago.Service.CuponService;
import com.example.MicroservicioDePago.Service.InscripcionService;
import com.example.MicroservicioDePago.Service.PagoService;

@SpringBootTest
public class PagoServiceTest {

    @Autowired
    private InscripcionService sInscripcion;

    @Autowired
    private CuponService cuponService;

    @Autowired
    private PagoService pagoService;

    @BeforeEach
    public void setUp() {
        Inscripcion existenteInscripcion = sInscripcion.buscarPorNombreYTitulo("test 1", "test de inscripcion");
        if (existenteInscripcion == null) {
            InscripcionRequest requestInscripcion = new InscripcionRequest();
            requestInscripcion.setNombre("test 1");
            requestInscripcion.setTitulo("test de inscripcion");
            sInscripcion.guardarInscripcion(requestInscripcion);
        }

        Cupon existenteCupon = cuponService.obtenerPorCodigo("TEST10");
        if (existenteCupon == null) {
            CuponRequest requestCupon = new CuponRequest();
            requestCupon.setCodigo("TEST10");
            requestCupon.setDescuento(10.0);
            requestCupon.setFechaExpiracion(Date.valueOf("2025-12-31")); 
            cuponService.registrarCupon(requestCupon);
        }
    }
    
    @Test
    public void testRegistrarPago() {
        Inscripcion inscripcion = sInscripcion.buscarPorNombreYTitulo("test 1", "test de inscripcion");
        Cupon cupon = cuponService.obtenerPorCodigo("TEST10");

        PagoRequest pagoRequest = new PagoRequest();
        pagoRequest.setPrecio(1000);
        pagoRequest.setFechaPago(LocalDate.now());
        pagoRequest.setIdInscripcion(inscripcion.getId_inscripcion());  
        pagoRequest.setIdCupon(cupon.getIdCupon());              
        pagoRequest.setIdTransaccionWebpay("TXN123456");

        Pago pagoCreado = pagoService.guardarNuevo(pagoRequest);

        assertNotNull(pagoCreado);
        assertEquals(1000, pagoCreado.getPrecio());
        assertEquals(inscripcion.getId_inscripcion(), pagoCreado.getInscripcion().getId_inscripcion());
        assertEquals(cupon.getIdCupon(), pagoCreado.getCupon().getIdCupon());
        assertEquals("TXN123456", pagoCreado.getIdTransaccionWebpay());
    }
}
