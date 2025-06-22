package com.anatonelly.freteexpress.service;

import com.anatonelly.freteexpress.exceptions.RegraNegocioException;
import com.anatonelly.freteexpress.model.EmpresaCliente;
import com.anatonelly.freteexpress.repository.EmpresaClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
// Implementar UserDetailsService é essencial para o login da empresa funcionar
public class EmpresaClienteService implements UserDetailsService {

    private final EmpresaClienteRepository empresaClienteRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmpresaClienteService(EmpresaClienteRepository empresaClienteRepository, PasswordEncoder passwordEncoder) {
        this.empresaClienteRepository = empresaClienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public EmpresaCliente cadastrarEmpresa(EmpresaCliente empresa) {
        // Valida se o email já existe
        if (empresaClienteRepository.findByEmail(empresa.getEmail()).isPresent()) {
            throw new RegraNegocioException("Já existe uma empresa cadastrada com este email.");
        }
        // Valida se o CNPJ já existe
        if (empresaClienteRepository.findByCnpj(empresa.getCnpj()).isPresent()) {
            throw new RegraNegocioException("Já existe uma empresa cadastrada com este CNPJ.");
        }

        String senhaCriptografada = passwordEncoder.encode(empresa.getSenha());
        empresa.setSenha(senhaCriptografada);

        return empresaClienteRepository.save(empresa);
    }
    
    public EmpresaCliente findByEmail(String email) {
        return empresaClienteRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Empresa não encontrada com o email: " + email));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        EmpresaCliente empresa = findByEmail(email);
        return new User(empresa.getEmail(), empresa.getSenha(), new ArrayList<>());
    }
}