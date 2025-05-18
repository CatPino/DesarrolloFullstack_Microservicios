package com.edutech.gestion_usuario_soporte.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edutech.gestion_usuario_soporte.model.Permiso;

public interface PermisoRepository extends JpaRepository<Permiso, Long> {

    //Buscar permiso por nombre exacto
    Optional<Permiso> findByNombre(String nombre);

    //Verificar si un permiso existe por nombre
    boolean existsByNombre(String nombre);

    //Buscar todos los permisos que contengan una palabra
    List<Permiso> findByNombreContainingIgnoreCase(String nombreParcial);

    //Buscar permisos ordenados alfabéticamente
    List<Permiso> findAllByOrderByNombreAsc();
}
