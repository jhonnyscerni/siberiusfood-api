package br.com.siberius.siberiusfood.api.model.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioComSenhaDTO extends UsuarioInputDTO {

    private String senha;
}
