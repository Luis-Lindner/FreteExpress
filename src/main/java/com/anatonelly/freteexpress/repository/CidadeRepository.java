package com.anatonelly.freteexpress.repository;

import com.anatonelly.freteexpress.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
    Cidade findByNome(String nome); // Método para buscar cidade pelo nome
}