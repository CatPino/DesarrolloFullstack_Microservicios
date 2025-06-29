package com.edutech.gestion_usuario_soporte.repository;

import com.edutech.gestion_usuario_soporte.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByPrimerNombre(String primerNombre);

    Optional<Usuario> findByCorreo(String correo);

    boolean existsByCorreo(String correo);
}
