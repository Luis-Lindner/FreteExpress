package com.anatonelly.freteexpress.service; // PACOTE CORRIGIDO

import com.anatonelly.freteexpress.model.Empresa; // IMPORT CORRIGIDO
import com.anatonelly.freteexpress.repository.EmpresaClienteRepository; // IMPORT CORRIGIDO
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpresaClienteService {

    @Autowired
    private EmpresaClienteRepository empresaClienteRepository;

    public Empresa cadastrarEmpresa(Empresa empresa) throws Exception {
        if (empresaClienteRepository.findByEmail(empresa.getEmail()) != null) {
            throw new Exception("Já existe uma empresa cadastrada com este email.");
        }
        if (empresaClienteRepository.findByCnpj(empresa.getCnpj()) != null) {
            throw new Exception("Já existe uma empresa cadastrada com este CNPJ.");
        }

        return empresaClienteRepository.save(empresa);
    }
}