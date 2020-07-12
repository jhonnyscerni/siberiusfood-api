package br.com.siberius.siberiusfood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeIdInputDTO {

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    private Long id;
}
