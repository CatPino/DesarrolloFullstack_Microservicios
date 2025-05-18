
package com.edutech.gestion_usuario_soporte.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.edutech.gestion_usuario_soporte.model.Usuario;
import com.edutech.gestion_usuario_soporte.repository.UsuarioRepository;

@Service 
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//Registra un nuevo usuario con su contraseña hasheada
     public Usuario registrarUsuario(Usuario usuario) {
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        return usuarioRepository.save(usuario);
    }

// Buscar usuario por correo electrónico

    public Optional<Usuario> buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    public Optional<Usuario> autenticarUsuario(String correo, String contrasenaIngresada) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (passwordEncoder.matches(contrasenaIngresada, usuario.getContrasena())) {
                return usuarioOpt;
            }
        }
        return Optional.empty();
    }
}
