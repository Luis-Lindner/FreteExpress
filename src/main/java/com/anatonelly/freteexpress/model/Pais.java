package com.anatonelly.freteexpress.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Pais")
public class Pais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pais")
    private Integer idPais;

    @Column(name = "nome", length = 45, nullable = false, unique = true)
    private String nome;

    // --- Construtores ---

    /**
     * Construtor vazio que o JPA (Hibernate) precisa para funcionar.
     */
    public Pais() {
    }

    /**
     * Construtor usado pelo nosso serviço para criar um novo país apenas com o nome.
     * @param nome O nome do país.
     */
    public Pais(String nome) {
        this.nome = nome;
    }


    // --- Getters e Setters ---

    public Integer getIdPais() {
        return idPais;
    }

    public void setIdPais(Integer idPais) {
        this.idPais = idPais;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}