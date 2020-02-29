package br.com.siberius.siberiusfood.api.model.mixin;

import br.com.siberius.siberiusfood.model.Estado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class CidadeMixin {

    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Estado estado;
}
