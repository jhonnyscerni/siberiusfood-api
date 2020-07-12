package br.com.siberius.siberiusfood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemPedidoDTO {

    private Long id;

    @ApiModelProperty(example = "1")
    private String produtoId;

    @ApiModelProperty(example = "Porco com molho agridoce")
    private String produtoNome;

    @ApiModelProperty(example = "78.90")
    private BigDecimal precoUnitario;

    @ApiModelProperty(example = "157.80")
    private BigDecimal precoTotal;

    @ApiModelProperty(example = "2")
    private Integer quantidade;

    @ApiModelProperty(example = "Menos picante, por favor")
    private String observacao;
}
