package br.com.siberius.siberiusfood.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long Id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    private Boolean ativo;

    @ManyToOne
    private Restaurante restaurante;
}
