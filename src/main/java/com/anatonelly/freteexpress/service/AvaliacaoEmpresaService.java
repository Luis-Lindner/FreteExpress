package com.anatonelly.freteexpress.service;

import com.anatonelly.freteexpress.model.AvaliacaoEmpresa;
import com.anatonelly.freteexpress.repository.AvaliacaoEmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoEmpresaService {
    private final AvaliacaoEmpresaRepository repository;

    public AvaliacaoEmpresaService(AvaliacaoEmpresaRepository repository) {
        this.repository = repository;
    }

    public List<AvaliacaoEmpresa> listarTodas() {
        return repository.findAll();
    }

    public Optional<AvaliacaoEmpresa> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public AvaliacaoEmpresa salvar(AvaliacaoEmpresa avaliacaoEmpresa) {
        return repository.save(avaliacaoEmpresa);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

}
