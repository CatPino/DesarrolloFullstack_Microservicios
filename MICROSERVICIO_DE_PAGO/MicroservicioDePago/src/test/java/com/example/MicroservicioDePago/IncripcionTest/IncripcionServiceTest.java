package com.example.MicroservicioDePago.IncripcionTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.MicroservicioDePago.Model.Entities.Inscripcion;
import com.example.MicroservicioDePago.Model.Request.InscripcionRequest;
import com.example.MicroservicioDePago.Service.InscripcionService;


@SpringBootTest
public class IncripcionServiceTest {

    @Autowired
    private InscripcionService sInscripcion;

    @BeforeEach
    public void setUp() {
        Inscripcion existente = sInscripcion.buscarPorNombreYTitulo("test 1", "test de inscripcion");
        if (existente == null) {
            InscripcionRequest request = new InscripcionRequest();
            request.setNombre("test 1");
            request.setTitulo("test de inscripcion");
            sInscripcion.guardarInscripcion(request);
            }
    }
    @Test
    public void testBuscarPorNombreYTitulo_CuandoExiste_DeberiaRetornarInscripcion() {
        Inscripcion resultado = sInscripcion.buscarPorNombreYTitulo("test 1", "test de inscripcion");
        assertNotNull(resultado);
        assertEquals("test 1", resultado.getNombre());
        assertEquals("test de inscripcion", resultado.getTitulo());
    }

    @Test
    public void testBuscarPorNombreYTitulo_CuandoNoExiste_DeberiaRetornarNull() {
        Inscripcion resultado = sInscripcion.buscarPorNombreYTitulo("Pedro", "Pasteleria");
        assertNull(resultado);
    }
}

    

