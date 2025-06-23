package com.anatonelly.freteexpress.repository;

import com.anatonelly.freteexpress.model.Motorista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MotoristaRepository extends JpaRepository<Motorista, Long> {

    Optional<Motorista> findByEmail(String email);

    // Métodos adicionados para validação
    Optional<Motorista> findByCpf(String cpf);
    Optional<Motorista> findByCnh(String cnh);
}