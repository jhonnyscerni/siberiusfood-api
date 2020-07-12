package br.com.siberius.siberiusfood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EstadoInputDTO {

    @ApiModelProperty(example = "Minas Gerais", required = true)
    @NotBlank
    private String nome;
}
