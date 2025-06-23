package com.anatonelly.freteexpress.repository;

import com.anatonelly.freteexpress.model.Motorista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // Import para Optional

@Repository
public interface MotoristaRepository extends JpaRepository<Motorista, Integer> { // idMotorista é Integer no modelo

    // Adicionado este método para permitir a busca de Motorista por email.
    // O Spring Data JPA automaticamente cria a implementação para este método.
    Optional<Motorista> findByEmail(String email);
}
