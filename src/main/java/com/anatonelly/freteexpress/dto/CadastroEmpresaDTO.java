package com.anatonelly.freteexpress.dto;

// Esta classe não tem anotações de @Entity ou @Id. É só um transportador de dados.
public class CadastroEmpresaDTO {

    // Dados da Empresa
    private String nomeEmpresa;
    private String email;
    private String senha;
    private String cnpj;

    // Dados do Endereço
    private String rua;
    private String numero;
    private String bairro;
    private String cep;
    private String complemento;
    private String cidadeNome;
    private String estadoNome;
    private String paisNome = "Brasil"; // Podemos fixar um valor padrão

    // Getters e Setters para todos os campos...

    public String getNomeEmpresa() { return nomeEmpresa; }
    public void setNomeEmpresa(String nomeEmpresa) { this.nomeEmpresa = nomeEmpresa; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getRua() { return rua; }
    public void setRua(String rua) { this.rua = rua; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }

    public String getCidadeNome() { return cidadeNome; }
    public void setCidadeNome(String cidadeNome) { this.cidadeNome = cidadeNome; }

    public String getEstadoNome() { return estadoNome; }
    public void setEstadoNome(String estadoNome) { this.estadoNome = estadoNome; }

    public String getPaisNome() { return paisNome; }
    public void setPaisNome(String paisNome) { this.paisNome = paisNome; }
}