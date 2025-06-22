package com.anatonelly.freteexpress.service;

import com.anatonelly.freteexpress.dto.CadastroEmpresaDTO;
import com.anatonelly.freteexpress.model.*;
import com.anatonelly.freteexpress.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional; // Certifique-se que Optional está importado

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
        // Agora findByNome retorna Optional, permitindo o uso de orElseGet
        Pais pais = paisRepository.findByNome(dto.getPaisNome())
                .orElseGet(() -> paisRepository.save(new Pais(dto.getPaisNome())));

        // Estado agora aceita um objeto Pais completo no construtor ou setter
        Estado estado = estadoRepository.findByNome(dto.getEstadoNome())
                .orElseGet(() -> {
                    Estado novoEstado = new Estado();
                    novoEstado.setNome(dto.getEstadoNome());
                    novoEstado.setPais(pais); // Garante que o País seja setado
                    return estadoRepository.save(novoEstado);
                });

        // Cidade agora aceita um objeto Estado completo no construtor ou setter
        Cidade cidade = cidadeRepository.findByNome(dto.getCidadeNome())
                .orElseGet(() -> {
                    Cidade novaCidade = new Cidade();
                    novaCidade.setNome(dto.getCidadeNome());
                    novaCidade.setEstado(estado); // Garante que o Estado seja setado
                    return cidadeRepository.save(novaCidade);
                });

        Endereco endereco = new Endereco();
        endereco.setRua(dto.getRua());
        endereco.setBairro(dto.getBairro());
        endereco.setCep(dto.getCep());
        endereco.setComplemento(dto.getComplemento());
        endereco.setCidade(cidade);

        try {
            // Se o número estiver em branco ou null, não tente parsear
            if (dto.getNumero() != null && !dto.getNumero().trim().isEmpty()) {
                endereco.setNumero(Integer.parseInt(dto.getNumero()));
            } else {
                endereco.setNumero(null); // Define como null se não for fornecido ou for inválido
            }
        } catch (NumberFormatException e) {
            System.err.println("AVISO: O número de endereço fornecido não é um inteiro válido: '" + dto.getNumero() + "'");
            endereco.setNumero(null); // Define como null em caso de erro de conversão
        }

        EmpresaCliente empresa = new EmpresaCliente();
        empresa.setNome(dto.getNomeEmpresa());
        empresa.setEmail(dto.getEmail());
        empresa.setSenha(passwordEncoder.encode(dto.getSenha()));
        empresa.setCnpj(dto.getCnpj());
        empresa.setEndereco(endereco);
        // Os demais campos (descricao, telefone, site, imagemPerfil) não estão no DTO ou sendo setados aqui
        // Se eles forem necessários e forem nulos, o Hibernate pode reclamar se a coluna for NOT NULL

        empresaClienteRepository.save(empresa);
    }
}
