package com.anatonelly.freteexpress.service;

import com.anatonelly.freteexpress.model.UsuarioAdministrador;
import com.anatonelly.freteexpress.repository.UsuarioAdministradorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioAdministradorService {

    private final UsuarioAdministradorRepository repository;

    public UsuarioAdministradorService(UsuarioAdministradorRepository repository) {
        this.repository = repository;
    }

    public List<UsuarioAdministrador> listarTodos() {
        return repository.findAll();
    }

    public Optional<UsuarioAdministrador> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public UsuarioAdministrador salvar(UsuarioAdministrador usuario) {
        return repository.save(usuario);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public Optional<UsuarioAdministrador> autenticar(String email, String senha) {
        return repository.findByEmailAndSenha(email, senha);
    }
}
