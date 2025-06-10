package com.anatonelly.freteexpress.repository; // PACOTE CORRIGIDO

import com.anatonelly.freteexpress.model.Empresa; // IMPORT CORRIGIDO
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaClienteRepository extends JpaRepository<Empresa, Integer> {
    Empresa findByEmail(String email);
    Empresa findByCnpj(String cnpj);
}