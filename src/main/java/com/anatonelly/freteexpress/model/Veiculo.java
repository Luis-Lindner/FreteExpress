package com.anatonelly.freteexpress.model;

import jakarta.persistence.*;

@Entity
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroRenavam;
    private String especificacoes;
    private Double altura;
    private Double comprimento;
    private Double largura;
    private Integer quantidadeEixos;
    private Boolean possuiLona;
    private String categoria;
    private String tipoCarroceria;

    @OneToOne
    @JoinColumn(name = "motorista_id")
    private Motorista motorista;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroRenavam() {
        return numeroRenavam;
    }

    public void setNumeroRenavam(String numeroRenavam) {
        this.numeroRenavam = numeroRenavam;
    }

    public String getEspecificacoes() {
        return especificacoes;
    }

    public void setEspecificacoes(String especificacoes) {
        this.especificacoes = especificacoes;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getComprimento() {
        return comprimento;
    }

    public void setComprimento(Double comprimento) {
        this.comprimento = comprimento;
    }

    public Double getLargura() {
        return largura;
    }

    public void setLargura(Double largura) {
        this.largura = largura;
    }

    public Integer getQuantidadeEixos() {
        return quantidadeEixos;
    }

    public void setQuantidadeEixos(Integer quantidadeEixos) {
        this.quantidadeEixos = quantidadeEixos;
    }

    public Boolean getPossuiLona() {
        return possuiLona;
    }

    public void setPossuiLona(Boolean possuiLona) {
        this.possuiLona = possuiLona;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTipoCarroceria() {
        return tipoCarroceria;
    }

    public void setTipoCarroceria(String tipoCarroceria) {
        this.tipoCarroceria = tipoCarroceria;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }
}

