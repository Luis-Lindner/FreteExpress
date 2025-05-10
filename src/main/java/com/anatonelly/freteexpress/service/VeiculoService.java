package com.anatonelly.freteexpress.service;

import com.anatonelly.freteexpress.model.Veiculo;
import com.anatonelly.freteexpress.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    public List<Veiculo> listarTodos() {
        return veiculoRepository.findAll();
    }

    public Optional<Veiculo> buscarPorId(Long id) {
        return veiculoRepository.findById(id);
    }

    public Veiculo salvar(Veiculo veiculo) {
        return veiculoRepository.save(veiculo);
    }

    public Optional<Veiculo> atualizar(Long id, Veiculo atualizado) {
        return veiculoRepository.findById(id).map(veiculo -> {
            veiculo.setNumeroRenavam(atualizado.getNumeroRenavam());
            veiculo.setEspecificacoes(atualizado.getEspecificacoes());
            veiculo.setAltura(atualizado.getAltura());
            veiculo.setComprimento(atualizado.getComprimento());
            veiculo.setLargura(atualizado.getLargura());
            veiculo.setQuantidadeEixos(atualizado.getQuantidadeEixos());
            veiculo.setPossuiLona(atualizado.getPossuiLona());
            veiculo.setCategoria(atualizado.getCategoria());
            veiculo.setTipoCarroceria(atualizado.getTipoCarroceria());
            veiculo.setMotorista(atualizado.getMotorista());
            return veiculoRepository.save(veiculo);
        });
    }

    public boolean deletar(Long id) {
        if (veiculoRepository.existsById(id)) {
            veiculoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

