package com.example.MicroservicioDePago.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.example.MicroservicioDePago.Model.Entities.Cupon;
import com.example.MicroservicioDePago.Model.Request.CursoRequest;
import com.example.MicroservicioDePago.Model.Request.PagoRequest;
import com.example.MicroservicioDePago.Model.Request.UsuarioRequest;
import com.example.MicroservicioDePago.Model.Response.ConfirmarResponse;
import com.example.MicroservicioDePago.Model.Response.InicioPagoResponse;
import com.example.MicroservicioDePago.Service.CuponService;
import com.example.MicroservicioDePago.Service.InscripcionService;
import com.example.MicroservicioDePago.Service.PagoService;

import cl.transbank.common.IntegrationType;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.exception.TransactionCommitException;
import cl.transbank.webpay.exception.TransactionCreateException;
import cl.transbank.webpay.webpayplus.WebpayPlus;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionCommitResponse;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionCreateResponse;

import java.time.LocalDate;
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

    // Configuración CORRECTA para WebpayPlus (versión 6.0.0+)
    public WebpayController() {
        WebpayOptions options = new WebpayOptions(
            "597055555532", // Código de comercio TEST
            "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C", 
            IntegrationType.TEST // Ambiente de integración
        );
        
        this.transaction = new WebpayPlus.Transaction(options);
    }

    // Memoria temporal para sesión de pago
    private final Map<String, Long> userMap = new HashMap<>();
    private final Map<String, Long> cursoMap = new HashMap<>();
    private final Map<String, Integer> precioMap = new HashMap<>();
    private final Map<String, String> cuponMap = new HashMap<>(); 


    @PostMapping("/crear")
    public InicioPagoResponse iniciarPago(@RequestBody PagoRequest request) {
        InicioPagoResponse response = new InicioPagoResponse();

        try {
            // Validar usuario
            UsuarioRequest usuario = webClient.get()
                .uri("http://localhost:8081/usuarios/" + request.getIdUsuario())
                .retrieve()
                .bodyToMono(UsuarioRequest.class)
                .block();

            if (usuario == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario no existe");
            }

            // Validar curso
            CursoRequest curso = webClient.get()
                .uri("http://localhost:8082/cursos/" + request.getIdCurso())
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

          
            String buyOrder = "orden-" + UUID.randomUUID();
            String sessionId = UUID.randomUUID().toString();
            String returnUrl = "http://localhost:8080/webpay/confirmar";

            // Guardar sesión temporal
            userMap.put(sessionId, request.getIdUsuario());
            cursoMap.put(sessionId, request.getIdCurso());
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
            response.setMensaje("Transacción creada correctamente");

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
    public ConfirmarResponse confirmarPago(@RequestParam("token_ws") String tokenWs) {
        ConfirmarResponse response = new ConfirmarResponse();

        try {
            // Confirmar transacción con WebPay
            WebpayPlusTransactionCommitResponse commitResponse = transaction.commit(tokenWs);

            // Recuperar datos de sesión (incluyendo cupón)
            String sessionId = commitResponse.getSessionId();
            Long userId = userMap.get(sessionId);
            Long cursoId = cursoMap.get(sessionId);
            Integer precio = precioMap.get(sessionId);
            String codigoCupon = cuponMap.get(sessionId); // Obtenemos el cupón

            // Guardar en base de datos
            sPago.guardarPago(userId, cursoId, precio, LocalDate.now(), codigoCupon, commitResponse.getBuyOrder());

            // Crear inscripción
            sInscripcion.registrarInscripcion(userId, cursoId, LocalDate.now());

            // Limpiar datos temporales (incluyendo cupón)
            userMap.remove(sessionId);
            cursoMap.remove(sessionId);
            precioMap.remove(sessionId);
            cuponMap.remove(sessionId);

            response.setExito(true);
            response.setMensaje("Pago exitoso e inscripción registrada. ID Transacción: " + commitResponse.getBuyOrder());

        } catch (TransactionCommitException e) {
            response.setExito(false);
            response.setMensaje("Error al confirmar el pago con WebPay: " + e.getMessage());
        } catch (Exception e) {
            response.setExito(false);
            response.setMensaje("Error al procesar la confirmación: " + e.getMessage());
        }

        return response;
    }
}