package com.anatonelly.freteexpress.model;

import jakarta.persistence.*;
import lombok.Data; // <<< Certifique-se que Lombok está importado e @Data está presente
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Empresa")
@Data // <<< Esta anotação gera os getters e setters
@NoArgsConstructor
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEmpresa;

    // ATENÇÃO AQUI: O nome do atributo deve ser 'endereco' (sem 'ç')
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_endereco") // Nome da coluna FK no DB
    private Endereco endereco; // <<< O nome do atributo deve ser 'endereco'

    @Column(name = "nome", length = 100)
    private String nome;

    @Column(name = "razaoSocial", length = 100)
    private String razaoSocial;

    @Column(name = "cnpj", length = 14)
    private String cnpj;

    @Column(name = "email", length = 45) // <<< 'email' para getEmail()
    private String email;

    @Column(name = "senha", length = 255) // <<< 'senha' para getSenha()
    private String senha;

    // ... (outros atributos)
}