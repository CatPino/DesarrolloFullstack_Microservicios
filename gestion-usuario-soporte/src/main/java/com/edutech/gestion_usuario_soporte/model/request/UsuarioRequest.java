package com.edutech.gestion_usuario_soporte.model.request;



import lombok.Data;

@Data
public class UsuarioRequest {

    private String nombre;
    private String correo;
    private String contrasena;
}
