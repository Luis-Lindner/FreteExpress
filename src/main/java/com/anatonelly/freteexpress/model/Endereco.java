package com.anatonelly.freteexpress.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Endereco")
@Data
@NoArgsConstructor
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_endereco") // Mapeia para id_endereco no DB
    private Integer idEndereco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cidade") // Nome da coluna FK no DB
    private Cidade cidade;

    @Column(name = "rua", length = 45)
    private String rua;

    @Column(name = "numero") // Tipo INT no DB
    private Integer numero;

    @Column(name = "bairro", length = 45)
    private String bairro;

    @Column(name = "cep", length = 45)
    private String cep;

    @Column(name = "complemento", length = 45)
    private String complemento;
}