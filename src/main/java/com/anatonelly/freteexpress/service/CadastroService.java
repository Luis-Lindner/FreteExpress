package com.anatonelly.freteexpress.service;

import com.anatonelly.freteexpress.dto.CadastroEmpresaDTO;
import com.anatonelly.freteexpress.model.*;
import com.anatonelly.freteexpress.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CadastroService {

    private final PaisRepository paisRepository;
    private final EstadoRepository estadoRepository;
    private final CidadeRepository cidadeRepository;
    private final EmpresaClienteRepository empresaClienteRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CadastroService(PaisRepository paisRepository, EstadoRepository estadoRepository, CidadeRepository cidadeRepository, EmpresaClienteRepository empresaClienteRepository, PasswordEncoder passwordEncoder) {
        this.paisRepository = paisRepository;
        this.estadoRepository = estadoRepository;
        this.cidadeRepository = cidadeRepository;
        this.empresaClienteRepository = empresaClienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Optional<EmpresaCliente> cadastrarNovaEmpresa(CadastroEmpresaDTO dto) {
        try {
            // Validação básica
            if (dto == null || dto.getEmail() == null || dto.getEmail().trim().isEmpty() || !dto.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                throw new IllegalArgumentException("Email inválido ou ausente.");
            }
            if (dto.getCnpj() == null || dto.getCnpj().trim().length() != 14) { // CNPJ deve ter 14 dígitos
                throw new IllegalArgumentException("CNPJ inválido. Deve conter 14 dígitos.");
            }
            if (dto.getSenha() == null || dto.getSenha().trim().isEmpty()) {
                throw new IllegalArgumentException("Senha ausente ou inválida.");
            }

            // Criar ou buscar entidades relacionadas
            Pais pais = paisRepository.findByNome(dto.getPaisNome())
                    .orElseGet(() -> paisRepository.save(new Pais(dto.getPaisNome())));

            Estado estado = estadoRepository.findByNome(dto.getEstadoNome())
                    .orElseGet(() -> estadoRepository.save(new Estado(dto.getEstadoNome(), pais)));

            Cidade cidade = cidadeRepository.findByNome(dto.getCidadeNome())
                    .orElseGet(() -> cidadeRepository.save(new Cidade(dto.getCidadeNome(), estado)));

            Endereco endereco = new Endereco();
            endereco.setRua(dto.getRua());
            endereco.setBairro(dto.getBairro());
            endereco.setCep(dto.getCep());
            endereco.setComplemento(dto.getComplemento());
            endereco.setCidade(cidade);

            // Tratar número do endereço
            if (dto.getNumero() != null && !dto.getNumero().trim().isEmpty()) {
                try {
                    endereco.setNumero(Integer.parseInt(dto.getNumero()));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Número de endereço inválido: " + dto.getNumero(), e);
                }
            }

            // Criar e salvar empresa
            EmpresaCliente empresa = new EmpresaCliente();
            empresa.setNome(dto.getNomeEmpresa());
            empresa.setEmail(dto.getEmail());
            empresa.setSenha(passwordEncoder.encode(dto.getSenha()));
            empresa.setCnpj(dto.getCnpj());
            empresa.setEndereco(endereco);

            EmpresaCliente savedEmpresa = empresaClienteRepository.save(empresa);
            return Optional.of(savedEmpresa);

        } catch (IllegalArgumentException e) {
            System.err.println("Erro de validação no cadastro: " + e.getMessage());
            return Optional.empty();
        } catch (Exception e) {
            System.err.println("Erro inesperado no cadastro: " + e.getMessage());
            return Optional.empty();
        }
    }
}