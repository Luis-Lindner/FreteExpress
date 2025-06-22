package com.anatonelly.freteexpress.repository;

import com.anatonelly.freteexpress.model.EmpresaCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // Import necessário

@Repository
public interface EmpresaClienteRepository extends JpaRepository<EmpresaCliente, Long> {

    Optional<EmpresaCliente> findByEmail(String email);
    Optional<EmpresaCliente> findByCnpj(String cnpj);
}