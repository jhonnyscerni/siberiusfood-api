package br.com.siberius.siberiusfood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FormaPagamentoIdInputDTO {

    @NotNull
    private Long id;
}
