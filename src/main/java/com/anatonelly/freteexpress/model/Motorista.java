package com.anatonelly.freteexpress.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString; // Importe o ToString se ainda quiser que ele gere outros toStrings

import java.io.Serializable; // Importe Serializable

@Entity
@Table(name = "Motorista")
@Data // Gerado automaticamente: getters, setters, equals, hashCode, e toString (com cuidado)
@NoArgsConstructor
public class Motorista implements Serializable {

    private static final long serialVersionUID = 1L; // Adicione um serialVersionUID

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_motorista") // Mapeia para id_motorista no DB (INT)
    private Integer idMotorista; // Tipo Integer para corresponder a INT no DB

    // Relacionamento com Endereco - Mantenha LAZY para performance geral
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_endereco")
    // @ToString.Exclude // Removido. Vamos sobrescrever toString() para ter controle total.
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
    @Column(name = "imagem_perfil", columnDefinition = "MEDIUMBLOB")
    private byte[] imagemPerfil;

    @Column(name = "status_pagamento", length = 50)
    private String statusPagamento;

    @Column(name = "avaliacao")
    private Integer avaliacao;

    // Sobrescrevendo toString() para que ele NÃO acesse propriedades Lazy,
    // evitando LazyInitializationException quando o Spring Security tenta usar o principal.
    @Override
    public String toString() {
        return email; // Ou nomeCompleto, algo que não depende de relações LAZY
    }
}
