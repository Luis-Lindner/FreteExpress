package com.anatonelly.freteexpress.repository;

import com.anatonelly.freteexpress.model.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // Import para Optional

@Repository
public interface PaisRepository extends JpaRepository<Pais, Integer> {
    // Corrigido para retornar Optional<Pais>
    Optional<Pais> findByNome(String nome);
}
