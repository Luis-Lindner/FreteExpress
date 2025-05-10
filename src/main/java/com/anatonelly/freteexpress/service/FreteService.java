package com.anatonelly.freteexpress.service;

import com.anatonelly.freteexpress.model.Frete;
import com.anatonelly.freteexpress.repository.FreteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FreteService {

    @Autowired
    private FreteRepository freteRepository;

    public List<Frete> listarTodos() {
        return freteRepository.findAll();
    }

    public Optional<Frete> buscarPorId(Long id) {
        return freteRepository.findById(id);
    }

    public Frete salvar(Frete frete) {
        return freteRepository.save(frete);
    }

    public Optional<Frete> atualizar(Long id, Frete freteAtualizado) {
        return freteRepository.findById(id).map(frete -> {
            frete.setOrigem(freteAtualizado.getOrigem());
            frete.setDestino(freteAtualizado.getDestino());
            frete.setTipoCarga(freteAtualizado.getTipoCarga());
            frete.setPeso(freteAtualizado.getPeso());
            frete.setDimensoes(freteAtualizado.getDimensoes());
            frete.setPrazoEntrega(freteAtualizado.getPrazoEntrega());
            frete.setDescricaoCarga(freteAtualizado.getDescricaoCarga());
            frete.setValorFrete(freteAtualizado.getValorFrete());
            frete.setEmpresaCliente(freteAtualizado.getEmpresaCliente());
            return freteRepository.save(frete);
        });
    }

    public boolean deletar(Long id) {
        if (freteRepository.existsById(id)) {
            freteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

