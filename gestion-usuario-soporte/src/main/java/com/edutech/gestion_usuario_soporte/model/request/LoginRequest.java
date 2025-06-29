package com.edutech.gestion_usuario_soporte.model.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String correo;
    private String contrasena;
}
