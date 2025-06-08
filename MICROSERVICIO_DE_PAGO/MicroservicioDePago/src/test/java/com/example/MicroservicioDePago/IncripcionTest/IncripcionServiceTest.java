package com.example.MicroservicioDePago.IncripcionTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.MicroservicioDePago.Model.Entities.Inscripcion;
import com.example.MicroservicioDePago.Service.InscripcionService;

@SpringBootTest
public class IncripcionServiceTest {

    @Autowired
    private InscripcionService sInscripcion;

    @Test
    public void testBuscarPorNombreYTitulo_CuandoExiste_DeberiaRetornarInscripcion() {
        Inscripcion resultado = sInscripcion.buscarPorNombreYTitulo("Catalina", "Curso de java");

        assertNotNull(resultado);

        assertEquals("Catalina", resultado.getNombre());
        assertEquals("Curso de Java", resultado.getTitulo());
    }

    @Test
    public void testBuscarPorNombreYTitulo_CuandoNoExiste_DeberiaRetornarNull() {
        Inscripcion resultado = sInscripcion.buscarPorNombreYTitulo("Pedro", "Pasteleria");
        assertNull(resultado);
    }
}
    

