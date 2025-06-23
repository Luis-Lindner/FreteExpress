package com.anatonelly.freteexpress.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Veiculo") // Nome da tabela no DB
@Data // Garante getters e setters
@NoArgsConstructor
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_veiculo") // Mapeia para id_veiculo no DB (INT)
    private Integer idVeiculo;

    // ATRIBUTOS DE RELACIONAMENTO (FKs)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carroceria") // Mapeia para id_carroceria no DB (INT)
    private Carroceria carroceria; // Assumindo que você tem uma classe Carroceria

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_veiculo") // Mapeia para id_tipo_veiculo no DB (INT)
    private TipoVeiculo tipoVeiculo; // Assumindo que você tem uma classe TipoVeiculo

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_motorista") // Mapeia para id_motorista no DB (INT)
    private Motorista motorista;

    // --- ATRIBUTOS ADICIONADOS PARA RESOLVER ERROS ---
    @Column(name = "placa", length = 8) // Exemplo de tamanho, ajuste conforme DB
    private String placa;

    @Column(name = "modelo", length = 100) // Exemplo de tamanho
    private String modelo;

    @Column(name = "ano") // Ano pode ser Integer no Java e INT no DB
    private Integer ano;

    @Column(name = "tipo", length = 50) // 'tipo' aqui refere-se ao 'tipo' vindo do formulário, pode ser o nome
    private String tipo;

    // Outros campos existentes, caso não estejam no DTO
    @Column(name = "ant", length = 8) // Verifique o tipo no DB
    private String ant;

    @Column(name = "renavam", length = 11, unique = true) // Renavam deve ser único
    private String renavam;

    @Column(name = "eixos")
    private Integer eixos;

    @Column(name = "lona") // TINYINT no DB pode ser Boolean ou Byte no Java
    private Boolean lona; // Assumindo TINYINT como Boolean no Java


    // --- ATRIBUTOS JÁ EXISTENTES NO SEU SCRIPT SQL (Se não estiverem no seu modelo, adicione-os) ---
    // Hibernate: add column altura float(53)
    @Column(name = "altura")
    private Double altura; // ou float

    // Hibernate: add column categoria varchar(255)
    @Column(name = "categoria")
    private String categoria;

    // Hibernate: add column comprimento float(53)
    @Column(name = "comprimento")
    private Double comprimento; // ou float

    // Hibernate: add column especificacoes varchar(255)
    @Column(name = "especificacoes")
    private String especificacoes;

    // Hibernate: add column largura float(53)
    @Column(name = "largura")
    private Double largura; // ou float

    // Hibernate: add column numeroRenavam varchar(255)
    @Column(name = "numero_renavam") // O log pode mostrar numeroRenavam como numero_renavam
    private String numeroRenavam;

    // Hibernate: add column possuiLona bit
    @Column(name = "possui_lona") // O log pode mostrar possuiLona como possui_lona
    private Boolean possuiLona;

    // Hibernate: add column quantidadeEixos integer
    @Column(name = "quantidade_eixos") // O log pode mostrar quantidadeEixos como quantidade_eixos
    private Integer quantidadeEixos;

    // Hibernate: add column tipoCarroceria varchar(255)
    @Column(name = "tipo_carroceria") // O log pode mostrar tipoCarroceria como tipo_carroceria
    private String tipoCarroceria;
}
