package com.anatonelly.freteexpress.repository;

import com.anatonelly.freteexpress.model.Motorista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MotoristaRepository extends JpaRepository<Motorista, Long> {

    /**
     * ESTE É O MÉTODO QUE ESTÁ FALTANDO.
     * Busca um motorista no banco de dados pelo seu endereço de email.
     * O Spring Security (através do MotoristaService) usará este método para encontrar o usuário durante o login.
     *
     * @param email O email do motorista a ser buscado.
     * @return um Optional contendo o Motorista se encontrado, ou um Optional vazio caso contrário.
     */
    Optional<Motorista> findByEmail(String email);

}