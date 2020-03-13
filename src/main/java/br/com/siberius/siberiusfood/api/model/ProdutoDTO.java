package br.com.siberius.siberiusfood.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoDTO {

    private Long Id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    private Boolean ativo;
}
