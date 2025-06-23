package com.anatonelly.freteexpress.repository;

import com.anatonelly.freteexpress.model.EmpresaCliente;
import com.anatonelly.freteexpress.model.Frete;
import com.anatonelly.freteexpress.model.Motorista;
import com.anatonelly.freteexpress.enums.StatusFrete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreteRepository extends JpaRepository<Frete, Long> {

    List<Frete> findByMotoristaSolicitanteAndStatus(Motorista motorista, StatusFrete status);
    List<Frete> findByEmpresaClienteOrderByPrazoEntregaDesc(EmpresaCliente empresaCliente);
}