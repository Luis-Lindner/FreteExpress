package com.anatonelly.freteexpress.model;

import com.anatonelly.freteexpress.enums.StatusFrete; // Garanta que este caminho está correto
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private LocalDateTime prazoEntrega;
    private String descricaoCarga;

    // É uma boa prática usar BigDecimal para valores monetários para evitar erros de arredondamento
    private BigDecimal valorFrete;

    // O campo corrigido que usa o Enum.
    // O JPA salvará o nome do status (ex: "PENDENTE") como texto no banco de dados.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusFrete status;

    // --- INÍCIO DAS NOVAS MELHORIAS ---
    // Adicionando os campos para a tela de detalhes. Eles serão criados como novas
    // colunas no seu banco de dados na próxima vez que a aplicação iniciar,
    // graças ao 'spring.jpa.hibernate.ddl-auto=update'.

    private String veiculosApropriados;

    private String tipoCarroceria;

    // --- FIM DAS NOVAS MELHORIAS ---


    // Relacionamento com a entidade EmpresaCliente
    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private EmpresaCliente empresaCliente;

    // Relacionamento com a entidade Motorista
    @ManyToOne
    @JoinColumn(name = "motorista_id")
    private Motorista motoristaSolicitante;


    // --- Construtores ---

    /**
     * Construtor padrão sem argumentos.
     * O JPA precisa dele para criar instâncias da entidade.
     */
    public Frete() {
    }


    // --- Getters e Setters EXISTENTES (NENHUM FOI ALTERADO) ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getTipoCarga() {
        return tipoCarga;
    }

    public void setTipoCarga(String tipoCarga) {
        this.tipoCarga = tipoCarga;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getDimensoes() {
        return dimensoes;
    }

    public void setDimensoes(String dimensoes) {
        this.dimensoes = dimensoes;
    }

    public LocalDateTime getPrazoEntrega() {
        return prazoEntrega;
    }

    public void setPrazoEntrega(LocalDateTime prazoEntrega) {
        this.prazoEntrega = prazoEntrega;
    }

    public String getDescricaoCarga() {
        return descricaoCarga;
    }

    public void setDescricaoCarga(String descricaoCarga) {
        this.descricaoCarga = descricaoCarga;
    }

    public BigDecimal getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(BigDecimal valorFrete) {
        this.valorFrete = valorFrete;
    }

    public StatusFrete getStatus() {
        return status;
    }

    public void setStatus(StatusFrete status) {
        this.status = status;
    }

    public EmpresaCliente getEmpresaCliente() {
        return empresaCliente;
    }

    public void setEmpresaCliente(EmpresaCliente empresaCliente) {
        this.empresaCliente = empresaCliente;
    }

    public Motorista getMotoristaSolicitante() {
        return motoristaSolicitante;
    }

    public void setMotoristaSolicitante(Motorista motoristaSolicitante) {
        this.motoristaSolicitante = motoristaSolicitante;
    }

    // --- GETTERS E SETTERS PARA OS NOVOS CAMPOS ---

    public String getVeiculosApropriados() {
        return veiculosApropriados;
    }

    public void setVeiculosApropriados(String veiculosApropriados) {
        this.veiculosApropriados = veiculosApropriados;
    }

    public String getTipoCarroceria() {
        return tipoCarroceria;
    }

    public void setTipoCarroceria(String tipoCarroceria) {
        this.tipoCarroceria = tipoCarroceria;
    }
}