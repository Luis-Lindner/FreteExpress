package com.anatonelly.freteexpress.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Estado")
@Data
@NoArgsConstructor
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado") // Mapeia para id_estado no DB
    private Integer idEstado;

    @Column(name = "nome", length = 45)
    private String nome;

    @Column(name = "sigla", length = 45)
    private String sigla;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pais") // Nome da coluna FK no DB
    private Pais pais;
}