package com.anatonelly.freteexpress.repository;

import com.anatonelly.freteexpress.model.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Integer> {
    Pais findByNome(String nome); // Método para buscar país pelo nome
}