package com.anatonelly.freteexpress.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Motorista") // Nome da tabela no DB
@Data
@NoArgsConstructor
public class Motorista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_motorista") // Mapeia para id_motorista no DB (INT)
    private Integer idMotorista;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_endereco") // Mapeia para id_endereco no DB (INT)
    private Endereco endereco;

    @Column(name = "nome_completo", length = 150)
    private String nomeCompleto;

    @Column(name = "cpf", length = 11)
    private String cpf;

    @Column(name = "email", length = 45)
    private String email;

    @Column(name = "senha", length = 255)
    private String senha;

    @Column(name = "celular", length = 11)
    private String celular;

    @Column(name = "cnh", length = 11)
    private String cnh;

    @Lob
    @Column(name = "imagem_perfil")
    private byte[] imagemPerfil;

    @Column(name = "status_pagamento", length = 50)
    private String statusPagamento;

    @Column(name = "avaliacao")
    private Integer avaliacao;
}
    