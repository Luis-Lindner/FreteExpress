package com.anatonelly.freteexpress.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroRenavam;
    private String especificacoes;
    private Double altura;
    private Double comprimento;
    private Double largura;
    private Integer quantidadeEixos;
    private Boolean possuiLona;
    private String categoria;
    private String tipoCarroceria;

    @OneToOne
    @JoinColumn(name = "motorista_id")
    private Motorista motorista;
}
