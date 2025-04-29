package com.anatonelly.freteexpress.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Frete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String origem;
    private String destino;
    private String tipoCarga;
    private Double peso;
    private String dimensoes;
    private LocalDateTime prazoEntrega;
    private String descricaoCarga;
    private Double valorFrete;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private EmpresaCliente empresaCliente;
}
