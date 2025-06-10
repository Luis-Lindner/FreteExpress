package com.anatonelly.freteexpress.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Pais")
@Data
@NoArgsConstructor
public class Pais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pais") // Mapeia para id_pais no DB
    private Integer idPais;

    @Column(name = "nome", length = 45)
    private String nome;
}