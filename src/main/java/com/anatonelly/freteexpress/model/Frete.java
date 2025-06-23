package com.anatonelly.freteexpress.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.anatonelly.freteexpress.enums.StatusFrete;
import java.time.LocalDateTime; // Para prazoEntrega, se for o caso
// import java.math.BigDecimal; // Se você preferir BigDecimal para preco

@Entity
@Table(name = "Frete")
@Data
@NoArgsConstructor
public class Frete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_frete")
    private Integer idFrete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_motorista") // Verifica no seu DB se é id_motorista
    private Motorista motoristaSolicitante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_endereco_origem")
    private Endereco origem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_endereco_destino")
    private Endereco destino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id") // Verifica no seu DB se é empresa_id
    private EmpresaCliente empresaCliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_veiculo")
    private TipoVeiculo tipoVeiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carroceria")
    private Carroceria carroceria;


    @Column(name = "produto", length = 45)
    private String produto;

    @Column(name = "preco")
    private Double preco; // ou BigDecimal

    @Column(name = "complemento") // TINYINT no DB
    private Boolean complemento;

    @Column(name = "rastreador") // TINYINT no DB
    private Boolean rastreador;

    @Column(name = "obs", length = 500)
    private String obs;

    @Column(name = "volume")
    private Integer volume;

    @Column(name = "pesoTotal")
    private Integer pesoTotal;

    @Column(name = "formaPagamento", length = 20)
    private String formaPagamento;

    @Column(name = "adiantamento", length = 4)
    private String adiantamento;

    @Column(name = "pedagio") // TINYINT no DB
    private Boolean pedagio;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private StatusFrete status;

    // --- ATRIBUTOS ADICIONADOS / VERIFICADOS COM BASE NO ERRO ---
    // Estes são os campos que seu FreteService esperava no Frete
    @Column(name = "tipo_carga") // Assumindo nome de coluna para tipoCarga
    private String tipoCarga;

    @Column(name = "peso") // Este deve ser float(53) no DB, mapeia para Double
    private Double peso;

    @Column(name = "dimensoes") // Exemplo de coluna para Dimensoes
    private String dimensoes; // Pode ser String se for texto, ou um JSON/Embedded

    @Column(name = "prazo_entrega") // Assumindo nome de coluna para prazoEntrega
    private LocalDateTime prazoEntrega; // Mapeia para datetime(6) no DB

    @Column(name = "descricao_carga") // Assumindo nome de coluna para descricaoCarga
    private String descricaoCarga;

    @Column(name = "valor_frete") // Assumindo nome de coluna para valorFrete
    private Double valorFrete; // Mapeia para decimal(38,2) no DB, Double funciona

    // Outros atributos do seu script SQL que devem estar aqui:
    // ant CHAR(8), renavam CHAR(11) etc. (do Veiculo, mas alguns Frete podem ter isso diretamente)
    // Se esses campos 'tipoCarga', 'dimensoes', 'prazoEntrega', 'descricaoCarga', 'valorFrete'
    // não corresponderem exatamente aos nomes das colunas no seu DB,
    // o Hibernate tentará usar a estratégia de nomenclatura e pode falhar.
    // VERIFIQUE NO SEU SCRIPT SQL ORIGINAL SE ESSES NOMES SÃO IGUAIS AOS @Column(name="...")
    // ou ajuste os @Column(name="") aqui para corresponderem ao seu DB existente.
}
