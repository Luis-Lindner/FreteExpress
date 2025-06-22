package com.anatonelly.freteexpress.service;

import com.anatonelly.freteexpress.model.EmpresaCliente;
import com.anatonelly.freteexpress.model.Frete;
import com.anatonelly.freteexpress.model.Motorista;
import com.anatonelly.freteexpress.enums.StatusFrete; // Certifique-se que este enum existe e tem os valores
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

    // O ID da entidade Frete deve ser Long, se você usou Long no JpaRepository
    // Se o ID for Integer, ajuste aqui também
    public Optional<Frete> buscarPorId(Integer id) { // Alterado para Integer
        return freteRepository.findById(id);
    }

    public Frete salvar(Frete frete) {
        return freteRepository.save(frete);
    }

    public boolean deletar(Integer id) { // Alterado para Integer
        if (freteRepository.existsById(id)) {
            freteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // O ID da entidade Frete deve ser Long, se você usou Long no JpaRepository
    // Se o ID for Integer, ajuste aqui também
    public Optional<Frete> atualizar(Integer id, Frete freteAtualizado) { // Alterado para Integer
        return freteRepository.findById(id).map(freteExistente -> {
            // Certifique-se que os getters/setters abaixo estão na sua classe Frete.java
            freteExistente.setOrigem(freteAtualizado.getOrigem()); // Exemplo, verificar se existe na classe Frete
            freteExistente.setDestino(freteAtualizado.getDestino()); // Exemplo
            freteExistente.setTipoCarga(freteAtualizado.getTipoCarga()); // Exemplo
            freteExistente.setPeso(freteAtualizado.getPeso()); // Exemplo
            freteExistente.setDimensoes(freteAtualizado.getDimensoes()); // Exemplo
            freteExistente.setPrazoEntrega(freteAtualizado.getPrazoEntrega()); // Exemplo
            freteExistente.setDescricaoCarga(freteAtualizado.getDescricaoCarga()); // Exemplo
            freteExistente.setValorFrete(freteAtualizado.getValorFrete()); // Exemplo
            freteExistente.setStatus(freteAtualizado.getStatus()); // Exemplo
            freteExistente.setEmpresaCliente(freteAtualizado.getEmpresaCliente()); // Exemplo
            freteExistente.setMotoristaSolicitante(freteAtualizado.getMotoristaSolicitante()); // Exemplo
            return freteRepository.save(freteExistente);
        });
    }

    public List<Frete> getFretesFinalizadosPorMotorista(Motorista motorista) {
        return freteRepository.findByMotoristaSolicitanteAndStatus(motorista, StatusFrete.FINALIZADO);
    }

    public List<Frete> findByEmpresa(EmpresaCliente empresaCliente) {
        return freteRepository.findByEmpresaClienteOrderByPrazoEntregaDesc(empresaCliente);
    }

    public boolean solicitarFrete(Integer freteId, Integer motoristaId) { // <<<<< CORRIGIDO AQUI: Long para Integer
        Optional<Frete> freteOpt = freteRepository.findById(freteId);
        Optional<Motorista> motoristaOpt = motoristaRepository.findById(motoristaId);

        if (freteOpt.isPresent() && motoristaOpt.isPresent()) {
            Frete frete = freteOpt.get();
            if (frete.getStatus() == StatusFrete.PENDENTE) { // Certifique-se que StatusFrete.PENDENTE existe
                frete.setStatus(StatusFrete.SOLICITADO); // Certifique-se que StatusFrete.SOLICITADO existe
                frete.setMotoristaSolicitante(motoristaOpt.get()); // Certifique-se que este setter existe em Frete
                freteRepository.save(frete);
                return true;
            }
        }
        return false;
    }
}
