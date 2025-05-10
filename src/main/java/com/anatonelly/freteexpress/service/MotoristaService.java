package com.anatonelly.freteexpress.service;

import com.anatonelly.freteexpress.model.Motorista;
import com.anatonelly.freteexpress.repository.MotoristaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotoristaService {

    @Autowired
    private MotoristaRepository motoristaRepository;

    public List<Motorista> listarTodos() {
        return motoristaRepository.findAll();
    }

    public Optional<Motorista> buscarPorId(Long id) {
        return motoristaRepository.findById(id);
    }

    public Motorista salvar(Motorista motorista) {
        return motoristaRepository.save(motorista);
    }

    public Optional<Motorista> atualizar(Long id, Motorista atualizado) {
        return motoristaRepository.findById(id).map(motorista -> {
            motorista.setEmail(atualizado.getEmail());
            motorista.setSenha(atualizado.getSenha());
            motorista.setNomeCompleto(atualizado.getNomeCompleto());
            motorista.setCpf(atualizado.getCpf());
            motorista.setEnderecoCompleto(atualizado.getEnderecoCompleto());
            motorista.setCelular(atualizado.getCelular());
            motorista.setCnh(atualizado.getCnh());
            motorista.setNumeroAntt(atualizado.getNumeroAntt());
            motorista.setImagemPerfil(atualizado.getImagemPerfil());
            motorista.setVeiculo(atualizado.getVeiculo());
            return motoristaRepository.save(motorista);
        });
    }

    public boolean deletar(Long id) {
        if (motoristaRepository.existsById(id)) {
            motoristaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

