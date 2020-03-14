package br.com.siberius.siberiusfood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class PermissaoInputDTO {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String descricao;
}
