package com.anatonelly.freteexpress.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Motorista")
@Data
@NoArgsConstructor
public class Motorista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_motorista") // Mapeia para id_motorista no DB
    private Integer idMotorista;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_endereco") // Nome da coluna FK no DB
    private Endereco endereco;

    @Column(name = "nome_completo", length = 150) // Nome da coluna no DB
    private String nomeCompleto;

    @Column(name = "cpf", length = 11) // Nome da coluna no DB
    private String cpf;

    @Column(name = "email", length = 45) // Nome da coluna no DB
    private String email;

    @Column(name = "senha", length = 255) // Nome da coluna no DB
    private String senha;

    @Column(name = "celular", length = 11) // Nome da coluna no DB
    private String celular;

    @Column(name = "cnh", length = 11) // Nome da coluna no DB
    private String cnh;

    @Lob
    @Column(name = "imagem_perfil") // Nome da coluna no DB
    private byte[] imagemPerfil;
}