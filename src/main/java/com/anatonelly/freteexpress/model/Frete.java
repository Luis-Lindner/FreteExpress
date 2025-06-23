package com.anatonelly.freteexpress.model;

import com.anatonelly.freteexpress.enums.StatusFrete;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate; // MUDANÇA 1: Import trocado de LocalDateTime para LocalDate

@Entity
@Table(name = "fretes")
public class Frete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String origem;

    @Column(nullable = false)
    private String destino;

    @Column(nullable = false)
    private String tipoCarga;

    private Double peso;
    private String dimensoes;
    private String especieCarga;
    // MUDANÇA 2: Tipo do campo alterado para LocalDate para corresponder ao formulário
    private LocalDate prazoEntrega;

    private String descricaoCarga;
    private BigDecimal valorFrete;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusFrete status;

    private String veiculosApropriados;
    private String tipoCarroceria;

    // MUDANÇA 3: Novo campo adicionado para o formulário do Passo 3
    private LocalDate dataColeta;


    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private EmpresaCliente empresaCliente;

    @ManyToOne
    @JoinColumn(name = "motorista_id")
    private Motorista motoristaSolicitante;


    public Frete() {
    }


    // --- Getters e Setters ---

    // ... (Getters e Setters existentes para id, origem, destino, etc. não mudam)

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getOrigem() { return origem; }
    public void setOrigem(String origem) { this.origem = origem; }
    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }
    public String getTipoCarga() { return tipoCarga; }
    public void setTipoCarga(String tipoCarga) { this.tipoCarga = tipoCarga; }
    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }
    public String getDimensoes() { return dimensoes; }
    public void setDimensoes(String dimensoes) { this.dimensoes = dimensoes; }
    public String getDescricaoCarga() { return descricaoCarga; }
    public void setDescricaoCarga(String descricaoCarga) { this.descricaoCarga = descricaoCarga; }
    public BigDecimal getValorFrete() { return valorFrete; }
    public void setValorFrete(BigDecimal valorFrete) { this.valorFrete = valorFrete; }
    public StatusFrete getStatus() { return status; }
    public void setStatus(StatusFrete status) { this.status = status; }
    public EmpresaCliente getEmpresaCliente() { return empresaCliente; }
    public void setEmpresaCliente(EmpresaCliente empresaCliente) { this.empresaCliente = empresaCliente; }
    public Motorista getMotoristaSolicitante() { return motoristaSolicitante; }
    public void setMotoristaSolicitante(Motorista motoristaSolicitante) { this.motoristaSolicitante = motoristaSolicitante; }
    public String getVeiculosApropriados() { return veiculosApropriados; }
    public void setVeiculosApropriados(String veiculosApropriados) { this.veiculosApropriados = veiculosApropriados; }
    public String getTipoCarroceria() { return tipoCarroceria; }
    public void setTipoCarroceria(String tipoCarroceria) { this.tipoCarroceria = tipoCarroceria; }


    // MUDANÇA 4: Getters e Setters atualizados e novos adicionados

    public LocalDate getPrazoEntrega() {
        return prazoEntrega;
    }

    public void setPrazoEntrega(LocalDate prazoEntrega) {
        this.prazoEntrega = prazoEntrega;
    }

    public LocalDate getDataColeta() {
        return dataColeta;
    }

    public void setDataColeta(LocalDate dataColeta) {
        this.dataColeta = dataColeta;
    }

    public String getEspecieCarga() {
        return especieCarga;
    }

    public void setEspecieCarga(String especieCarga) {
        this.especieCarga = especieCarga;
    }
}