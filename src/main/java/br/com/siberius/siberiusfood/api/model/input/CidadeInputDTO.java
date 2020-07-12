package br.com.siberius.siberiusfood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeInputDTO {

    //NotBlank não possui implementação para ele
    @ApiModelProperty(example = "Uberlândia", required = true)
    @NotBlank
    private String nome;

    @Valid
    @NotNull
    private EstadoIdInputDTO estado;
}
