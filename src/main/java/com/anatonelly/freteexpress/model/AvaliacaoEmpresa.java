package com.anatonelly.freteexpress.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class AvaliacaoEmpresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Min(0)
    @Max(5)
    private Integer nota;

    @Column(length = 500)
    private String comentario;

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private EmpresaCliente empresaCliente;

    @ManyToOne
    @JoinColumn(name = "motorista_id", nullable = false)
    private Motorista motorista;

    public AvaliacaoEmpresa(Long id, Integer nota, String comentario, EmpresaCliente empresaCliente, Motorista motorista) {
        this.id = id;
        this.nota = nota;
        this.comentario = comentario;
        this.empresaCliente = empresaCliente;
        this.motorista = motorista;
    }
}