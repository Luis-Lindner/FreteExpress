package com.anatonelly.freteexpress.repository;

import com.anatonelly.freteexpress.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // Import para Optional

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {
    // Corrigido para retornar Optional<Estado>
    Optional<Estado> findByNome(String nome);
}
