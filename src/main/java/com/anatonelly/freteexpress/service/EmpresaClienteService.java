package com.anatonelly.freteexpress.service;

import com.anatonelly.freteexpress.model.EmpresaCliente;
import com.anatonelly.freteexpress.repository.EmpresaClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaClienteService {

    private final EmpresaClienteRepository repository;

    public EmpresaClienteService(EmpresaClienteRepository repository) {
        this.repository = repository;
    }

    public List<EmpresaCliente> listarTodas() {
        return repository.findAll();
    }

    public Optional<EmpresaCliente> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public EmpresaCliente salvar(EmpresaCliente empresaCliente) {
        return repository.save(empresaCliente);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
