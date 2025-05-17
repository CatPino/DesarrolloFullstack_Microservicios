package com.edutech.gestion_academica.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.gestion_academica.model.Certificado;

@Service
public class CertificadoService {

    @Autowired
    private certificadoRepository certificadoRepository;

    public Certificado emitirCertificado(Certificado certificado) {
        return certificadoRepository.save(certificado);
    }

    public List<Certificado> buscarPorUsuarioId(Long idUsuario) {
        return certificadoRepository.findByIdUsuario(idUsuario);
    }
}
