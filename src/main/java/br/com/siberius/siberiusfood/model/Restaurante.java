package br.com.siberius.siberiusfood.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@NotNull
    //@NotEmpty
    //@NotBlank
    @Column(nullable = false)
    private String nome;

    //@DecimalMin("0")
    //@Multiplo(numero = 10)
    //@TaxaFrete
    //@NotNull
    //@PositiveOrZero
    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;

    //	@JsonIgnoreProperties("hibernateLazyInitializer")
    //@JsonIgnore
    //@Valid
    //@NotNull
    //@ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
    @ManyToOne //(fetch = FetchType.LAZY)
    @JoinColumn(name = "cozinha_id", nullable = false)
    private Cozinha cozinha;

    @Embedded
    private Endereco endereco;

    private Boolean ativo = Boolean.TRUE;

    private Boolean aberto = Boolean.FALSE;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataAtualizacao;

    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private Set<FormaPagamento> formaPagamentos = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "restaurante_usuario_responsavel", joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private Set<Usuario> responsaveis = new HashSet<>();

    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos = new ArrayList<>();

    public void ativar() {
        setAtivo(true);
    }

    public void inativar() {
        setAtivo(false);
    }

    public boolean removerFormasPagamento(FormaPagamento formaPagamento) {
        return getFormaPagamentos().remove(formaPagamento);
    }

    public boolean adicionarFormarPagamento(FormaPagamento formaPagamento) {
        return getFormaPagamentos().add(formaPagamento);
    }

    public void abrir() {
        setAberto(Boolean.TRUE);
    }

    public void fechar() {
        setAberto(Boolean.FALSE);
    }

    public boolean adicionarResponsavel(Usuario usuario) {
        return getResponsaveis().add(usuario);
    }

    public boolean removerResponsavel(Usuario usuario) {
        return getResponsaveis().remove(usuario);
    }
}
