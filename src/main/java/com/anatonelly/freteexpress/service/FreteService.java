package com.anatonelly.freteexpress.service;

import com.anatonelly.freteexpress.model.Frete;
import com.anatonelly.freteexpress.model.Motorista;
import com.anatonelly.freteexpress.enums.StatusFrete;
import com.anatonelly.freteexpress.repository.FreteRepository;
import com.anatonelly.freteexpress.repository.MotoristaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FreteService {

    private final FreteRepository freteRepository;
    private final MotoristaRepository motoristaRepository;

    public FreteService(FreteRepository freteRepository, MotoristaRepository motoristaRepository) {
        this.freteRepository = freteRepository;
        this.motoristaRepository = motoristaRepository;
    }

    public List<Frete> listarTodos() {
        return freteRepository.findAll();
    }

    public Optional<Frete> buscarPorId(Long id) {
        return freteRepository.findById(id);
    }

    public Frete salvar(Frete frete) {
        return freteRepository.save(frete);
    }

    public boolean deletar(Long id) {
        if (freteRepository.existsById(id)) {
            freteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Frete> atualizar(Long id, Frete freteAtualizado) {
        return freteRepository.findById(id).map(freteExistente -> {
            freteExistente.setOrigem(freteAtualizado.getOrigem());
            freteExistente.setDestino(freteAtualizado.getDestino());
            freteExistente.setTipoCarga(freteAtualizado.getTipoCarga());
            freteExistente.setPeso(freteAtualizado.getPeso());
            freteExistente.setDimensoes(freteAtualizado.getDimensoes());
            freteExistente.setPrazoEntrega(freteAtualizado.getPrazoEntrega());
            freteExistente.setDescricaoCarga(freteAtualizado.getDescricaoCarga());
            freteExistente.setValorFrete(freteAtualizado.getValorFrete());
            freteExistente.setStatus(freteAtualizado.getStatus());
            freteExistente.setEmpresaCliente(freteAtualizado.getEmpresaCliente());
            freteExistente.setMotoristaSolicitante(freteAtualizado.getMotoristaSolicitante());
            return freteRepository.save(freteExistente);
        });
    }

    public List<Frete> getFretesFinalizadosPorMotorista(Motorista motorista) {
        return freteRepository.findByMotoristaSolicitanteAndStatus(motorista, StatusFrete.FINALIZADO);
    }

    public boolean solicitarFrete(Long freteId, Long motoristaId) {
        Optional<Frete> freteOpt = freteRepository.findById(freteId);
        Optional<Motorista> motoristaOpt = motoristaRepository.findById(motoristaId);

        if (freteOpt.isPresent() && motoristaOpt.isPresent()) {
            Frete frete = freteOpt.get();
            if (frete.getStatus() == StatusFrete.PENDENTE) {
                frete.setStatus(StatusFrete.SOLICITADO);
                frete.setMotoristaSolicitante(motoristaOpt.get());
                freteRepository.save(frete);
                return true;
            }
        }
        return false;
    }
}