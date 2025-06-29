package com.edutech.gestion_usuario_soporte.service;

import com.edutech.gestion_usuario_soporte.model.entity.Rol;
import com.edutech.gestion_usuario_soporte.model.entity.Usuario;
import com.edutech.gestion_usuario_soporte.model.request.UsuarioRequest;
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

    public Optional<Usuario> login(String correo, String contrasena) {
        if (correo == null || correo.isEmpty() || contrasena == null || contrasena.isEmpty()) {
            return Optional.empty();
        }

        Optional<Usuario> usuario = usuarioRepository.findByCorreo(correo);
        if (usuario.isPresent() && passwordEncoder.matches(contrasena, usuario.get().getContrasena())) {
            return usuario;
        }
        return Optional.empty();
    }

    public String registrar(UsuarioRequest usuarioRequest) {
        if (usuarioRepository.existsByCorreo(usuarioRequest.getCorreo())) {
            return "Correo ya registrado";
        }

        Optional<Rol> rol = rolRepository.findByNombre("ALUMNO");
        if (rol.isEmpty()) {
            return "Rol ALUMNO no existe";
        }

        Usuario usuario = new Usuario();
        usuario.setCorreo(usuarioRequest.getCorreo());
        usuario.setPrimerNombre(usuarioRequest.getNombre());
        usuario.setContrasena(passwordEncoder.encode(usuarioRequest.getContrasena()));
        usuario.setRol(rol.get());

        usuarioRepository.save(usuario);
        return "Usuario registrado exitosamente";
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerUsuarioPorNombre(String nombre) {
        return usuarioRepository.findByPrimerNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}
