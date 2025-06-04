package com.anatonelly.freteexpress.repository;

import com.anatonelly.freteexpress.model.EmpresaCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaClienteRepository extends JpaRepository<EmpresaCliente, Long> {
}
