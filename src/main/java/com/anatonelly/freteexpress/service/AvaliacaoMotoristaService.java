package com.anatonelly.freteexpress.service;

import com.anatonelly.freteexpress.model.AvaliacaoMotorista;
import com.anatonelly.freteexpress.repository.AvaliacaoMotoristaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoMotoristaService {
    private final AvaliacaoMotoristaRepository repository;

    public AvaliacaoMotoristaService(AvaliacaoMotoristaRepository repository) {
        this.repository = repository;
    }

    public List<AvaliacaoMotorista> listarTodas() {
        return repository.findAll();
    }

    public Optional<AvaliacaoMotorista> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public AvaliacaoMotorista salvar(AvaliacaoMotorista avaliacaoMotorista) {
        return repository.save(avaliacaoMotorista);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
