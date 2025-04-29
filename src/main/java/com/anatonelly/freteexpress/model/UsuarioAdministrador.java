package com.anatonelly.freteexpress.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UsuarioAdministrador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String senha;
}
