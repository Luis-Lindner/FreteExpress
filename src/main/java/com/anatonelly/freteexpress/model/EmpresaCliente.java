package com.anatonelly.freteexpress.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class EmpresaCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String senha;
    private String nomeEmpresa;
    private String razaoSocial;
    private String cnpj;
    private String descricao;
    private String enderecoCompleto;
    private String telefoneComercial;
    private String site;
}
