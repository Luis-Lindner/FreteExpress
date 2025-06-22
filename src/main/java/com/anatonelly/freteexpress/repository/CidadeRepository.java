package com.anatonelly.freteexpress.repository;

import com.anatonelly.freteexpress.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // Import para Optional

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
    // Corrigido para retornar Optional<Cidade>
    Optional<Cidade> findByNome(String nome);
}
