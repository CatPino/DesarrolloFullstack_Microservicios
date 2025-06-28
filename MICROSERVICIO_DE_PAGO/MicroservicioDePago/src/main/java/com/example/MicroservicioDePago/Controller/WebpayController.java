package com.example.MicroservicioDePago.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.example.MicroservicioDePago.Model.Entities.Cupon;
import com.example.MicroservicioDePago.Model.Entities.Inscripcion;
import com.example.MicroservicioDePago.Model.Request.CompraRequest;
import com.example.MicroservicioDePago.Model.Request.CursoRequest;
import com.example.MicroservicioDePago.Model.Request.InscripcionRequest;
import com.example.MicroservicioDePago.Model.Request.PagoRequest;
import com.example.MicroservicioDePago.Model.Request.UsuarioRequest;
import com.example.MicroservicioDePago.Model.Response.ConfirmarResponse;
import com.example.MicroservicioDePago.Model.Response.InicioPagoResponse;
import com.example.MicroservicioDePago.Service.CuponService;
import com.example.MicroservicioDePago.Service.InscripcionService;
import com.example.MicroservicioDePago.Service.PagoService;

import cl.transbank.common.IntegrationType;
import cl.transbank.webpay.common.WebpayOptions;

import cl.transbank.webpay.exception.TransactionCreateException;
import cl.transbank.webpay.webpayplus.WebpayPlus;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionCommitResponse;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionCreateResponse;
import io.swagger.v3.oas.annotations.Operation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("webpay")
public class WebpayController {

    @Autowired 
    private WebClient webClient;

    @Autowired 
    private PagoService sPago;

    @Autowired 
    private InscripcionService sInscripcion;

    @Autowired 
    private CuponService sCupon;

    // Configuración Webpay Plus
    private final WebpayPlus.Transaction transaction;

    public WebpayController() {
        WebpayOptions options = new WebpayOptions(
            "597055555532", 
            "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C", 
            IntegrationType.TEST 
        );
        
        this.transaction = new WebpayPlus.Transaction(options);
    }

    // Memoria temporal para sesión de pago
    private final Map<String, String> userMap = new HashMap<>();
    private final Map<String, String> cursoMap = new HashMap<>();
    private final Map<String, Integer> precioMap = new HashMap<>();
    private final Map<String, String> cuponMap = new HashMap<>(); 


    @PostMapping("/crear")
    @Operation(summary = "Iniciar pago con WebPay",description = "Crea una transacción con WebPay, valida al usuario y el curso, aplica descuento si hay cupón, y devuelve la URL para realizar el pago.")
    public InicioPagoResponse iniciarPago(@RequestBody CompraRequest request) {
        InicioPagoResponse response = new InicioPagoResponse();

        try {
            // Validar usuario
            UsuarioRequest usuario = webClient.get()
                .uri("http://localhost:8081/api/auth/usuario/" + request.getNombre())
                .retrieve()
                .bodyToMono(UsuarioRequest.class)
                .block();

            if (usuario == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario no existe");
            }

            // Validar curso
            CursoRequest curso = webClient.get()
                .uri("http://localhost:8082/api/cursos/titulo/" + request.getTitulo())
                .retrieve()
                .bodyToMono(CursoRequest.class)
                .block();

            if (curso == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Curso no existe");
            }

            // Calcular precio con descuento si aplica
            double precioFinal = curso.getPrecio();
            String codigoCupon = request.getCodigoCupon();

            if (request.getCodigoCupon() != null && !request.getCodigoCupon().isEmpty()) {
                try {
                    Cupon cupon = sCupon.validarCupon(request.getCodigoCupon());
                    precioFinal *= (1 - cupon.getDescuento() / 100.0);
                } catch (ResponseStatusException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cupón inválido o expirado");
                }
            }

            int precio = (int) Math.round(precioFinal);

          
            String buyOrder = "orden-" + UUID.randomUUID().toString().substring(0, 16);
            String sessionId = UUID.randomUUID().toString();
            String returnUrl = "http://localhost:8083/webpay/confirmar";

            // Guardar sesión temporal
            userMap.put(sessionId, request.getNombre());
            cursoMap.put(sessionId, request.getTitulo());
            precioMap.put(sessionId, precio);
            cuponMap.put(sessionId, codigoCupon); 

            // Crear transacción en WebPay
            WebpayPlusTransactionCreateResponse createResponse = transaction.create(
                buyOrder, 
                sessionId, 
                precio, 
                returnUrl
            );

            response.setExito(true);
            response.setUrlPago(createResponse.getUrl() + "?token_ws=" + createResponse.getToken());
            response.setMensaje("Transacción creada correctamente para curso: " + curso.getTitulo());

        } catch (TransactionCreateException e) {
            response.setExito(false);
            response.setMensaje("Error al crear transacción en WebPay: " + e.getMessage());
        } catch (Exception e) {
            response.setExito(false);
            response.setMensaje("Error al iniciar pago: " + e.getMessage());
        }

        return response;
    }

    @GetMapping("/confirmar")
    @Operation(summary = "Confirmar pago WebPay",description = "Confirma la transacción con WebPay, registra la inscripción del usuario y guarda el pago realizado. Limpia la sesión temporal después de confirmar.")
    public ConfirmarResponse confirmarPago(@RequestParam("token_ws") String tokenWs) {
    ConfirmarResponse response = new ConfirmarResponse();
    String sessionId = null;

    try {
        // 1. Confirmar transacción WebPay
        WebpayPlusTransactionCommitResponse commitResponse = transaction.commit(tokenWs);
        sessionId = commitResponse.getSessionId();

        // 2. Obtener datos de sesión
        String nombre = userMap.get(sessionId);
        String titulo = cursoMap.get(sessionId);
        int precio = precioMap.get(sessionId);
        String codigoCupon = cuponMap.get(sessionId);

        // 3. Registrar inscripción
        InscripcionRequest inscRequest = new InscripcionRequest();
        inscRequest.setNombre(nombre);
        inscRequest.setTitulo(titulo);
        Inscripcion nuevaInscripcion = sInscripcion.guardarInscripcion(inscRequest);

        // 4. Registrar pago
        PagoRequest pagoRequest = new PagoRequest();
        pagoRequest.setPrecio(precio);
        pagoRequest.setFechaPago(nuevaInscripcion.getFechaInscripcion());
        pagoRequest.setIdTransaccionWebpay(commitResponse.getBuyOrder());
        pagoRequest.setIdInscripcion(nuevaInscripcion.getId_inscripcion());
        
        if (codigoCupon != null && !codigoCupon.isEmpty()) {
            Cupon cupon = sCupon.validarCupon(codigoCupon);
            pagoRequest.setIdCupon(cupon.getIdCupon());
        }

        sPago.guardarNuevo(pagoRequest);

        // 5. Configurar respuesta
        response.setExito(true);
        response.setMensaje("Pago e inscripción registrados exitosamente ");

    } catch (ResponseStatusException e) {
        throw e;
    } catch (Exception e) {
        response.setExito(false);
        response.setMensaje("Error al confirmar pago: " + e.getMessage());
    } finally {
        // 6. Limpieza segura
        if (sessionId != null) {
            userMap.remove(sessionId);
            cursoMap.remove(sessionId);
            precioMap.remove(sessionId);
            cuponMap.remove(sessionId);
        }
    }
    return response;
    }
}