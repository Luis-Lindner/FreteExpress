package com.anatonelly.freteexpress.repository;

import com.anatonelly.freteexpress.model.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PaisRepository extends JpaRepository<Pais, Integer> {
    Optional<Pais> findByNome(String nome);
}