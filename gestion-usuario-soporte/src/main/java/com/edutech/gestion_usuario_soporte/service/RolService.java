package com.edutech.gestion_usuario_soporte.service;

import com.edutech.gestion_usuario_soporte.model.entity.Rol;
import com.edutech.gestion_usuario_soporte.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolService {

    private final RolRepository rolRepository;

    public List<Rol> listar() {
        return rolRepository.findAll();
    }

    public Optional<Rol> obtenerPorId(Long id) {
        return rolRepository.findById(id);
    }

    public String crear(Rol rol) {
        if (rolRepository.existsByNombre(rol.getNombre())) {
            return "Ya existe un rol con ese nombre.";
        }
        rolRepository.save(rol);
        return "Rol creado exitosamente";
    }

    public String actualizar(Long id, Rol nuevoRol) {
        return rolRepository.findById(id)
                .map(rol -> {
                    rol.setNombre(nuevoRol.getNombre());
                    rolRepository.save(rol);
                    return "Rol actualizado correctamente";
                })
                .orElse("Rol no encontrado");
    }

    public String eliminar(Long id) {
        if (!rolRepository.existsById(id)) {
            return "Rol no encontrado";
        }
        rolRepository.deleteById(id);
        return "Rol eliminado correctamente";
    }
}
