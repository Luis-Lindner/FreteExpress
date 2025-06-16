package com.anatonelly.freteexpress.repository; // PACOTE CORRIGIDO

import com.anatonelly.freteexpress.model.EmpresaCliente; // IMPORT CORRIGIDO
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaClienteRepository extends JpaRepository<EmpresaCliente, Integer> {
    EmpresaCliente findByEmail(String email);
    EmpresaCliente findByCnpj(String cnpj);
}