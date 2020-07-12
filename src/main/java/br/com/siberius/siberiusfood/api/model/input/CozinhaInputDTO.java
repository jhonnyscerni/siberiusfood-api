package br.com.siberius.siberiusfood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CozinhaInputDTO {

    @ApiModelProperty(example = "Brasileira", required = true)
    @NotBlank
    private String nome;
}
