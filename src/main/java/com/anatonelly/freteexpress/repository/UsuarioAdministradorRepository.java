package com.anatonelly.freteexpress.repository;

import com.anatonelly.freteexpress.model.UsuarioAdministrador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioAdministradorRepository extends JpaRepository<UsuarioAdministrador, Long> {
    Optional<UsuarioAdministrador> findByEmailAndSenha(String email, String senha);
}
