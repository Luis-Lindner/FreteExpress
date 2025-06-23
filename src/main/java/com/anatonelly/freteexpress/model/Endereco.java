package com.anatonelly.freteexpress.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Endereco") // Nome da tabela no DB
@Data
@NoArgsConstructor
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_endereco") // Mapeia para id_endereco no DB (INT)
    private Integer idEndereco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cidade") // Mapeia para id_cidade no DB (INT)
    private Cidade cidade;

    @Column(name = "rua", length = 45)
    private String rua;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "bairro", length = 45)
    private String bairro;

    @Column(name = "cep", length = 45)
    private String cep;

    @Column(name = "complemento", length = 45)
    private String complemento;
}
    