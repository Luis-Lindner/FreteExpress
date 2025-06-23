package com.anatonelly.freteexpress.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Empresa") // Nome da tabela no DB
@Data
@NoArgsConstructor
public class EmpresaCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empresa") // Mapeia para id_empresa no DB (INT)
    private Integer idEmpresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_endereco") // Mapeia para id_endereco no DB (INT)
    private Endereco endereco;

    @Column(name = "nome", length = 100)
    private String nome;

    @Column(name = "razao_social", length = 100) // Mapeia para razao_social no DB
    private String razaoSocial;

    @Column(name = "cnpj", length = 14)
    private String cnpj;

    @Column(name = "email", length = 45)
    private String email;

    @Column(name = "senha", length = 255)
    private String senha;

    @Column(name = "descricao", length = 200)
    private String descricao;

    @Column(name = "telefone", length = 12)
    private String telefone;

    @Column(name = "site", length = 300)
    private String site;

    @Lob
    @Column(name = "imagem_perfil") // Mapeia para imagem_perfil no DB
    private byte[] imagemPerfil;
}
    