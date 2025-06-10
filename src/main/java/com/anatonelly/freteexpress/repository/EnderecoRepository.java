package com.anatonelly.freteexpress.repository;

import com.anatonelly.freteexpress.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
    // Você pode adicionar métodos de busca específicos aqui, se precisar
}