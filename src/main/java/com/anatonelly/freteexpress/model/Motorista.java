package com.anatonelly.freteexpress.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "motoristas")
public class Motorista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(unique = true)
    private String cpf;

    @Column(unique = true)
    private String cnh;

    @OneToMany(mappedBy = "motoristaSolicitante")
    private List<Frete> fretes;

    // --- CÓDIGO QUE ESTAVA FALTANDO ---

    /**
     * Campo temporário para a lógica da tela.
     * A anotação @Transient impede que o JPA tente criar uma coluna para ele no banco.
     */
    @Transient
    private String statusPagamento;

    /**
     * Campo temporário para a lógica da tela.
     */
    @Transient
    private int avaliacao;

    // --- FIM DO CÓDIGO FALTANTE ---


    public Motorista() {
        // Construtor padrão
    }

    // --- GETTERS E SETTERS ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public List<Frete> getFretes() {
        return fretes;
    }

    public void setFretes(List<Frete> fretes) {
        this.fretes = fretes;
    }

    // --- GETTERS E SETTERS QUE ESTAVAM FALTANDO ---

    public String getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(String statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public int getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(int avaliaco) {
        this.avaliacao = avaliaco;
    }
}