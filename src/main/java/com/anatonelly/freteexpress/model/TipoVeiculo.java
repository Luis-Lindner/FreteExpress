package com.anatonelly.freteexpress.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TipoVeiculo") // Nome da tabela no DB
@Data // Garante getters e setters
@NoArgsConstructor
public class TipoVeiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_veiculo") // Mapeia para id_tipo_veiculo no DB (INT)
    private Integer idTipoVeiculo;

    @Column(name = "peso", length = 45) // Conforme seu script SQL
    private String peso;

    @Column(name = "tipo", length = 45) // Conforme seu script SQL
    private String tipo;
}
