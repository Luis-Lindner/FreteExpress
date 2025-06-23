package com.anatonelly.freteexpress.service;

import com.anatonelly.freteexpress.model.EmpresaCliente;
import com.anatonelly.freteexpress.repository.EmpresaClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmpresaClienteService {

    private final EmpresaClienteRepository empresaClienteRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmpresaClienteService(EmpresaClienteRepository empresaClienteRepository, PasswordEncoder passwordEncoder) {
        this.empresaClienteRepository = empresaClienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public EmpresaCliente cadastrarEmpresa(EmpresaCliente empresaCliente) {
        String senhaCriptografada = passwordEncoder.encode(empresaCliente.getSenha());
        empresaCliente.setSenha(senhaCriptografada);
        return empresaClienteRepository.save(empresaCliente);
    }

    public Optional<EmpresaCliente> findByEmail(String email) {
        return empresaClienteRepository.findByEmail(email);
    }
}
