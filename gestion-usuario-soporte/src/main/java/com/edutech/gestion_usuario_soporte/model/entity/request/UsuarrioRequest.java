package com.edutech.gestion_usuario_soporte.model.entity.request;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;

public class UsuarrioRequest {
   
    private String nombre;

    private String correo;

    private String contraseña;
}
