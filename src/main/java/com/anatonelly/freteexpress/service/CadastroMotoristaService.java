package com.anatonelly.freteexpress.service;

import com.anatonelly.freteexpress.dto.CadastroMotoristaDTO;
import com.anatonelly.freteexpress.exceptions.RegraNegocioException;
import com.anatonelly.freteexpress.model.*;
import com.anatonelly.freteexpress.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class CadastroMotoristaService {

    @Autowired private PaisRepository paisRepository;
    @Autowired private EstadoRepository estadoRepository;
    @Autowired private CidadeRepository cidadeRepository;
    @Autowired private MotoristaRepository motoristaRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Transactional
    public void cadastrarNovoMotorista(CadastroMotoristaDTO dto) throws IOException {
        // --- INÍCIO DA VALIDAÇÃO ---
        if (motoristaRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RegraNegocioException("Este email já está em uso. Por favor, utilize outro.");
        }
        if (dto.getCpf() != null && motoristaRepository.findByCpf(dto.getCpf()).isPresent()) {
            throw new RegraNegocioException("Este CPF já está cadastrado.");
        }
        if (dto.getCnh() != null && motoristaRepository.findByCnh(dto.getCnh()).isPresent()) {
            throw new RegraNegocioException("Esta CNH já está cadastrada.");
        }
        // --- FIM DA VALIDAÇÃO ---


        // 1. Lógica de Endereço (Encontrar ou Criar)
        Pais pais = paisRepository.findByNome("Brasil")
                .orElseGet(() -> paisRepository.save(new Pais("Brasil")));

        Estado estado = estadoRepository.findByNome(dto.getEstado())
                .orElseGet(() -> estadoRepository.save(new Estado(dto.getEstado(), pais)));

        Cidade cidade = cidadeRepository.findByNome(dto.getCidade())
                .orElseGet(() -> cidadeRepository.save(new Cidade(dto.getCidade(), estado)));

        Endereco endereco = new Endereco();
        endereco.setRua(dto.getRua());
        endereco.setNumero(dto.getNumero());
        endereco.setBairro(dto.getBairro());
        endereco.setCep(dto.getCep());
        endereco.setComplemento(dto.getComplemento());
        endereco.setCidade(cidade);

        // 2. Criar Motorista
        Motorista motorista = new Motorista();
        motorista.setNome(dto.getNome());
        motorista.setEmail(dto.getEmail());
        motorista.setSenha(passwordEncoder.encode(dto.getSenha()));
        motorista.setCpf(dto.getCpf());
        motorista.setCnh(dto.getCnh());
        motorista.setCelular(dto.getCelular());
        motorista.setEndereco(endereco);

        if (dto.getFoto() != null && !dto.getFoto().isEmpty()) {
            motorista.setFoto(dto.getFoto().getBytes());
        }

        // 3. Criar Veículo
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(dto.getPlaca());
        veiculo.setModelo(dto.getModelo());
        veiculo.setAno(dto.getAno());
        veiculo.setTipoCarroceria(dto.getTipo());

        // 4. Associar Veículo e Motorista (bidirecional)
        motorista.setVeiculo(veiculo);
        veiculo.setMotorista(motorista);

        // 5. Salvar (O Cascade fará o resto)
        motoristaRepository.save(motorista);
    }
}