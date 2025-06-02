package com.anatonelly.freteexpress.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

@Entity
public class Motorista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "CPF é obrigatório")
    private String cpf;

    @Column(nullable = false)
    @NotBlank(message = "Nome completo é obrigatório")
    private String nomeCompleto;

    @Column(nullable = false)
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Senha é obrigatória")
    private String senha;

    private String endereco;
    private String celular;
    private String cidade;
    private String estado;

    // Construtor vazio (substitui @NoArgsConstructor)
    public Motorista(String number, String testeMotoristaMySQL, String mail, String senha123) {
    }

    // Construtor parametrizado (já existente)
    public Motorista(Long id, String cpf, String nomeCompleto, String email, String senha, String endereco, String celular, String cidade, String estado) {
        this.id = id;
        this.cpf = cpf;
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
        this.celular = celular;
        this.cidade = cidade;
        this.estado = estado;
    }

    public Motorista() {

    }

    // Getters e Setters (substitui @Data)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // toString (substitui @Data)
    @Override
    public String toString() {
        return "Motorista{" +
                "id=" + id +
                ", cpf='" + cpf + '\'' +
                ", nomeCompleto='" + nomeCompleto + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", endereco='" + endereco + '\'' +
                ", celular='" + celular + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }

    // equals e hashCode (substitui @Data)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Motorista motorista = (Motorista) o;
        return Objects.equals(id, motorista.id) &&
                Objects.equals(cpf, motorista.cpf) &&
                Objects.equals(nomeCompleto, motorista.nomeCompleto) &&
                Objects.equals(email, motorista.email) &&
                Objects.equals(senha, motorista.senha) &&
                Objects.equals(endereco, motorista.endereco) &&
                Objects.equals(celular, motorista.celular) &&
                Objects.equals(cidade, motorista.cidade) &&
                Objects.equals(estado, motorista.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cpf, nomeCompleto, email, senha, endereco, celular, cidade, estado);
    }
}