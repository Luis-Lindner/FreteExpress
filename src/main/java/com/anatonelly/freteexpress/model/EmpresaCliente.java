package com.anatonelly.freteexpress.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString; // Importe ToString

import java.io.Serializable; // Importe Serializable

@Entity
@Table(name = "Empresa")
@Data // Gerado automaticamente: getters, setters, equals, hashCode, e toString (com cuidado)
@NoArgsConstructor
public class EmpresaCliente implements Serializable { // Implementa Serializable

    private static final long serialVersionUID = 1L; // Adicione um serialVersionUID

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empresa")
    private Integer idEmpresa;

    // Relacionamento com Endereco - Mantenha LAZY
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_endereco")
    // @ToString.Exclude // Removido
    private Endereco endereco;

    @Column(name = "nome", length = 100)
    private String nome;

    @Column(name = "razao_social", length = 100)
    private String razaoSocial;

    @Column(name = "cnpj", length = 14)
    private String cnpj;

    @Column(name = "email", length = 45)
    private String email;

    @Column(name = "senha", length = 255)
    private String senha;

    @Column(name = "descricao", length = 200)
    private String descricao;

    @Column(name = "telefone", length = 12)
    private String telefone;

    @Column(name = "site", length = 300)
    private String site;

    @Lob
    @Column(name = "imagem_perfil", columnDefinition = "MEDIUMBLOB")
    private byte[] imagemPerfil;

    // Sobrescrevendo toString() para que ele NÃO acesse propriedades Lazy,
    // evitando LazyInitializationException quando o Spring Security tenta usar o principal.
    @Override
    public String toString() {
        return email; // Ou razaoSocial, algo que não depende de relações LAZY
    }
}
