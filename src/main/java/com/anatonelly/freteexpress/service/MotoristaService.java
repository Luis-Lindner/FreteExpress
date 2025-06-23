package com.anatonelly.freteexpress.service;

import com.anatonelly.freteexpress.model.Motorista;
import com.anatonelly.freteexpress.repository.MotoristaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Importe PasswordEncoder
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotoristaService {

    private final MotoristaRepository motoristaRepository;
    private final PasswordEncoder passwordEncoder; // Injeta o PasswordEncoder

    @Autowired
    public MotoristaService(MotoristaRepository motoristaRepository, PasswordEncoder passwordEncoder) {
        this.motoristaRepository = motoristaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Motorista salvar(Motorista motorista) {
        String senhaCriptografada = passwordEncoder.encode(motorista.getSenha());
        motorista.setSenha(senhaCriptografada);
        return motoristaRepository.save(motorista);
    }

    public List<Motorista> listar() {
        return motoristaRepository.findAll();
    }

    public Optional<Motorista> findByEmail(String email) {
        return motoristaRepository.findByEmail(email);
    }
}
