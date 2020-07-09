package br.com.siberius.siberiusfood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EstadoIdInputDTO {

    //Nesse casso como anotation @ApiModelProperty ele tem o required padrão false e sobrescreve o BeanValidatorPluginsConfiguration
    @ApiModelProperty(example = "1", required = true)
    @NotNull
    private Long id;
}
