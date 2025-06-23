package com.anatonelly.freteexpress.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Estado")
@Data
@NoArgsConstructor // Necessário para o JPA
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEstado;

    @Column(name = "nome", length = 45)
    private String nome;

    @Column(name = "sigla", length = 45)
    private String sigla;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pais")
    private Pais pais;

    // ADICIONE ESTE CONSTRUTOR MANUALMENTE
    // Ele corresponde exatamente ao que seu serviço precisa
    public Estado(String nome, Pais pais) {
        this.nome = nome;
        this.pais = pais;
    }
}