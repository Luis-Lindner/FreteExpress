package com.anatonelly.freteexpress.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class AvaliacaoMotorista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer nota;
    private String comentario;

    @ManyToOne
    @JoinColumn(name = "motorista_id")
    private Motorista motorista;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private com.anatonelly.freteexpress.model.Empresa empresa;
}
