package com.edutech.gestion_usuario_soporte.service;

import com.edutech.gestion_usuario_soporte.model.entity.Rol;
import com.edutech.gestion_usuario_soporte.model.entity.Usuario;
import com.edutech.gestion_usuario_soporte.repository.RolRepository;
import com.edutech.gestion_usuario_soporte.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<Usuario> login(String correo, String contraseña) {
        Optional<Usuario> usuario = usuarioRepository.findByCorreo(correo);
        if (usuario.isPresent() && passwordEncoder.matches(contraseña, usuario.get().getContraseña())) {
            return usuario;
        }
        return Optional.empty();
    }

    public String registrar(Usuario usuario) {
    

    if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
        return "Correo ya registrado";
    }

    Optional<Rol> rol = rolRepository.findByNombre("ALUMNO");
    if (rol.isEmpty()) {
        return "Rol ALUMNO no existe";
    }

    usuario.setRol(rol.get());
    usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
    usuarioRepository.save(usuario);
    return "Usuario registrado exitosamente";
}

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }
}
