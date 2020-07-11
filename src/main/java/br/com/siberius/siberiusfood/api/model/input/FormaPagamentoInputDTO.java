package br.com.siberius.siberiusfood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FormaPagamentoInputDTO {

    @ApiModelProperty(example = "Cartão de crédito", required = true)
    @NotNull
    private String descricao;
}
