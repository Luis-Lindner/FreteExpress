package com.anatonelly.freteexpress.model;

import com.anatonelly.freteexpress.enums.TipoCarroceria;
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

    private String fotoNome;
    private String placa;
    private String modelo;
    private int anoFabricacao;

    @Enumerated(EnumType.STRING)
    private TipoCarroceria tipoCarroceria;

    @Column(nullable = true)
    private String statusPagamento; // Novo campo para status de pagamento

    @Column(nullable = true)
    private int avaliacao; // Novo campo para avaliação (0 a 5)

    public Motorista() {
    }

    public Motorista(Long id, String cpf, String nomeCompleto, String email, String senha, String endereco, String celular, String cidade, String estado,
                     String fotoNome, String placa, String modelo, int anoFabricacao, TipoCarroceria tipoCarroceria,
                     String statusPagamento, int avaliacao) {
        this.id = id;
        this.cpf = cpf;
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
        this.celular = celular;
        this.cidade = cidade;
        this.estado = estado;
        this.fotoNome = fotoNome;
        this.placa = placa;
        this.modelo = modelo;
        this.anoFabricacao = anoFabricacao;
        this.tipoCarroceria = tipoCarroceria;
        this.statusPagamento = statusPagamento;
        this.avaliacao = avaliacao;
    }

    // Getters e Setters existentes...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getFotoNome() { return fotoNome; }
    public void setFotoNome(String fotoNome) { this.fotoNome = fotoNome; }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public int getAnoFabricacao() { return anoFabricacao; }
    public void setAnoFabricacao(int anoFabricacao) { this.anoFabricacao = anoFabricacao; }
    public TipoCarroceria getTipoCarroceria() { return tipoCarroceria; }
    public void setTipoCarroceria(TipoCarroceria tipoCarroceria) { this.tipoCarroceria = tipoCarroceria; }

    // Novos Getters e Setters
    public String getStatusPagamento() { return statusPagamento; }
    public void setStatusPagamento(String statusPagamento) { this.statusPagamento = statusPagamento; }
    public int getAvaliacao() { return avaliacao; }
    public void setAvaliacao(int avaliacao) { this.avaliacao = avaliacao; }

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
                ", fotoNome='" + fotoNome + '\'' +
                ", placa='" + placa + '\'' +
                ", modelo='" + modelo + '\'' +
                ", anoFabricacao=" + anoFabricacao +
                ", tipoCarroceria=" + tipoCarroceria +
                ", statusPagamento='" + statusPagamento + '\'' +
                ", avaliacao=" + avaliacao +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Motorista)) return false;
        Motorista that = (Motorista) o;
        return anoFabricacao == that.anoFabricacao &&
                avaliacao == that.avaliacao &&
                Objects.equals(id, that.id) &&
                Objects.equals(cpf, that.cpf) &&
                Objects.equals(nomeCompleto, that.nomeCompleto) &&
                Objects.equals(email, that.email) &&
                Objects.equals(senha, that.senha) &&
                Objects.equals(endereco, that.endereco) &&
                Objects.equals(celular, that.celular) &&
                Objects.equals(cidade, that.cidade) &&
                Objects.equals(estado, that.estado) &&
                Objects.equals(fotoNome, that.fotoNome) &&
                Objects.equals(placa, that.placa) &&
                Objects.equals(modelo, that.modelo) &&
                tipoCarroceria == that.tipoCarroceria &&
                Objects.equals(statusPagamento, that.statusPagamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cpf, nomeCompleto, email, senha, endereco, celular, cidade, estado, fotoNome, placa, modelo, anoFabricacao, tipoCarroceria, statusPagamento, avaliacao);
    }
}