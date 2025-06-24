package com.anatonelly.freteexpress.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data // Anotação do Lombok para gerar Getters, Setters, etc.
public class CadastroMotoristaDTO {

    // Etapa 1
    private String email;
    private String senha;
    private String nome;
    private String cpf;
    private String cnh;

    // Etapa 2
    private String rua;
    private Integer numero;
    private String bairro;
    private String cep;
    private String complemento;
    private String cidade;
    private String estado;
    private String celular;

    // Etapa 3
    private MultipartFile foto;

    // Etapa 4
    private String placa;
    private String modelo;
    private Integer ano;
    private String tipo; // Tipo de Veículo/Carroceria
}