package br.com.siberius.siberiusfood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioComSenhaDTO extends UsuarioInputDTO {

    @ApiModelProperty(example = "123", required = true)
    private String senha;
}
