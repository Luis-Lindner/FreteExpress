package com.anatonelly.freteexpress.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Carroceria") // Nome da tabela no DB
@Data // Garante getters e setters
@NoArgsConstructor
public class Carroceria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carroceria") // Mapeia para id_carroceria no DB (INT)
    private Integer idCarroceria;

    @Column(name = "tipo", length = 45) // Conforme seu script SQL
    private String tipo;

    @Column(name = "carroceria", length = 45) // Conforme seu script SQL
    private String carroceria;
}
