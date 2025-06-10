package com.anatonelly.freteexpress.repository;

import com.anatonelly.freteexpress.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {
    Estado findByNome(String nome); // Método para buscar estado pelo nome
}