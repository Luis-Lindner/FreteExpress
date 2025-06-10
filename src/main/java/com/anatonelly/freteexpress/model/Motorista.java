package com.anatonelly.freteexpress.model;

import com.anatonelly.freteexpress.enums.TipoCarroceria;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Motorista")
@Data
@NoArgsConstructor
public class Motorista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_motorista")
    private Long idMotorista;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;

    @Column(name = "nome_completo", length = 150)
    @NotBlank(message = "Nome completo é obrigatório")
    @Size(max = 150, message = "Nome completo deve ter no máximo 150 caracteres")
    private String nomeCompleto;

    @Column(name = "cpf", length = 11)
    @NotBlank(message = "CPF é obrigatório")
    @Size(min = 11, max = 11, message = "CPF deve ter exatamente 11 dígitos")
    @Pattern(regexp = "^\\d{11}$", message = "CPF deve conter apenas números")
    private String cpf;

    @Column(name = "email", length = 45)
    @Email(message = "E-mail inválido")
    private String email;

    @Column(name = "senha", length = 255)
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    private String senha;

    @Column(name = "celular", length = 11)
    @Pattern(regexp = "^\\d{10,11}$", message = "Celular deve ter 10 ou 11 dígitos")
    private String celular;

    @Column(name = "cnh", length = 11)
    @NotBlank(message = "CNH é obrigatória")
    @Size(min = 11, max = 11, message = "CNH deve ter exatamente 11 dígitos")
    private String cnh;

    @Lob
    @Column(name = "imagem_perfil")
    private byte[] imagemPerfil;

    @Column(name = "cidade", length = 100)
    private String cidade;

    @Column(name = "estado", length = 2)
    @Size(max = 2, message = "Estado deve ter no máximo 2 caracteres")
    private String estado;

    @Column(name = "foto_nome", length = 100)
    private String fotoNome;

    @Column(name = "placa", length = 8)
    @Pattern(regexp = "^[A-Z]{3}\\d[A-Z]\\d{2}$", message = "Placa deve seguir o formato AAA0A00")
    private String placa;

    @Column(name = "modelo", length = 50)
    private String modelo;

    @Column(name = "ano_fabricacao")
    @Min(value = 1900, message = "Ano de fabricação deve ser maior que 1900")
    private int anoFabricacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_carroceria")
    private TipoCarroceria tipoCarroceria;

    @Column(name = "status_pagamento", length = 20)
    private String statusPagamento;

    @Column(name = "avaliacao")
    @Min(value = 0, message = "Avaliação deve ser no mínimo 0")
    @Max(value = 5, message = "Avaliação deve ser no máximo 5")
    private Integer avaliacao;

    public Motorista(Long idMotorista, Endereco endereco, String nomeCompleto, String cpf, String email, String senha,
                     String celular, String cnh, byte[] imagemPerfil, String cidade, String estado, String fotoNome,
                     String placa, String modelo, int anoFabricacao, TipoCarroceria tipoCarroceria,
                     String statusPagamento, Integer avaliacao) {
        this.idMotorista = idMotorista;
        this.endereco = endereco;
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.celular = celular;
        this.cnh = cnh;
        this.imagemPerfil = imagemPerfil;
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
}