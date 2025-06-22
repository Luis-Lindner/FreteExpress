package com.anatonelly.freteexpress.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Estado")
@Data // Garante getters e setters
@NoArgsConstructor
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEstado;

    @Column(name = "nome", length = 45)
    private String nome;

    @Column(name = "sigla", length = 45)
    private String sigla; // Certifique-se que este atributo existe

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pais")
    private Pais pais;

    // Se você tiver algum setSigla() ou getSigla() manual aqui, remova-o.
    // O @Data se encarrega disso.
}
