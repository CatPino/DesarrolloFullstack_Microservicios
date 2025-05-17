package com.edutech.gestion_usuario_soporte.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edutech.gestion_usuario_soporte.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {

    //Buscar rol por nombre exacto
    Optional<Rol> findByNombre(String nombre);

    //Verificar existencia por nombre
    boolean existsByNombre(String nombre);

    //Buscar roles que contengan una palabra en el nombre (búsqueda parcial)
    List<Rol> findByNombreContainingIgnoreCase(String nombreParcial);

    //Buscar todos los roles ordenados por nombre ascendente
    List<Rol> findAllByOrderByNombreAsc();
}
