package com.anatonelly.freteexpress.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Motorista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String senha;
    private String nomeCompleto;
    private String cpf;
    private String enderecoCompleto;
    private String celular;
    private String cnh;
    private String numeroAntt;
    private byte[] imagemPerfil;

    @OneToOne(mappedBy = "motorista", cascade = CascadeType.ALL)
    private Veiculo veiculo;
}
