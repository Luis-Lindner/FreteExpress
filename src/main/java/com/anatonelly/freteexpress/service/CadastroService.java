package com.anatonelly.freteexpress.service;

import com.anatonelly.freteexpress.dto.CadastroEmpresaDTO;
import com.anatonelly.freteexpress.model.*;
import com.anatonelly.freteexpress.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void cadastrarNovaEmpresa(CadastroEmpresaDTO dto) {
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

        try {
            if (dto.getNumero() != null && !dto.getNumero().trim().isEmpty()) {
                endereco.setNumero(Integer.parseInt(dto.getNumero()));
            }
        } catch (NumberFormatException e) {
            System.err.println("AVISO: O número de endereço fornecido não é um inteiro válido: '" + dto.getNumero() + "'");
        }

        EmpresaCliente empresa = new EmpresaCliente();
        empresa.setNome(dto.getNomeEmpresa());
        empresa.setEmail(dto.getEmail());
        empresa.setSenha(passwordEncoder.encode(dto.getSenha()));
        empresa.setCnpj(dto.getCnpj());
        empresa.setEndereco(endereco);

        empresaClienteRepository.save(empresa);
    }
}