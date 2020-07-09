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

    @ApiModelProperty(example = "Uberlândia")
    @NotBlank
    private String nome;

    @Valid
    @NotNull
    private EstadoIdInputDTO estado;
}
