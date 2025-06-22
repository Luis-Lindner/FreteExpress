package com.anatonelly.freteexpress.model;

import jakarta.persistence.*;

@Entity
@Table(name = "empresas_cliente")
public class EmpresaCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String senha;
    private String cnpj;

    @OneToOne(cascade = CascadeType.ALL) // Salva o endereço junto com a empresa
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

    public EmpresaCliente() {
    }
    
    // Getters e Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public Endereco getEndereco() { return endereco; }
    public void setEndereco(Endereco endereco) { this.endereco = endereco; }
}