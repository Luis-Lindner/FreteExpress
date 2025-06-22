package com.anatonelly.freteexpress.repository;

import com.anatonelly.freteexpress.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EstadoRepository extends JpaRepository<Estado, Integer> {
    Optional<Estado> findByNome(String nome);
}