package com.edutech.gestion_usuario_soporte.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edutech.gestion_usuario_soporte.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    
    //Buscar un usuario por su correo electrónico.
     
    Optional<Usuario> findByCorreo(String correo);
    List<Usuario> findByRolNombre(String nombreRol);
    List<Usuario> findByEstado(String estado);
    boolean existsByCorreo(String correo);
}
