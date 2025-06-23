package com.anatonelly.freteexpress.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
// Certifique-se que o Lombok está no seu pom.xml

@Data // Gera getters, setters, toString, equals, hashCode
@NoArgsConstructor // Gera construtor sem argumentos
public class CadastroMotoristaDTO {
    // Campos do Motorista
    private String email;
    private String senha;
    private String nomeCompleto;
    private String cpf;
    private String celular;
    private String cnh; // Adicione se for capturado no form

    // Campos de Endereço (Serão mapeados para o objeto Endereco no controller)
    private String rua;
    private String numero; // String, para poder validar antes de parsear para Integer
    private String bairro;
    private String cep;
    private String complemento;

    // Campos de Cidade e Estado (Serão mapeados para os objetos Cidade e Estado)
    private String cidadeNome;
    private String estadoNome;
    private String paisNome; // Se você tiver um campo para País no formulário

    // Campos do Veículo (Se o cadastro for conjunto de motorista + veículo)
    private String placa;
    private String modelo;
    private Integer ano; // Pode ser Integer diretamente se a validação do form garante número
    private String tipo; // Ex: TipoVeiculo
    // ... outros campos do veículo, como eixos, lona, renavam, etc.
}
