package com.anatonelly.freteexpress.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AvaliacaoEmpresa") // Nome da tabela conforme o DB
@Data
@NoArgsConstructor
public class AvaliacaoEmpresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avaliacao_empresa") // Mapeia para id_avaliacao_empresa no DB
    private Integer idAvaliacaoEmpresa; // Usando Integer para consistência com o ER, mas Long é comum para IDs.

    @Column(nullable = false)
    @Min(0)
    @Max(5)
    private Integer nota;

    @Column(length = 200)
    private String comentario;

    // Relacionamento com a entidade Empresa
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa", nullable = false) // Nome da coluna FK no DB
    private Empresa empresa;

    // Relacionamento com a entidade Motorista
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_motorista", nullable = false) // Nome da coluna FK no DB
    private Motorista motorista;
}