package com.edutech.gestion_usuario_soporte.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RolRequest {

    @Schema(description = "Nombre del rol", example = "ALUMNO", required = true)
    private String nombre;
}
