package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.model.input.RestauranteInputDTO;
import br.com.siberius.siberiusfood.model.Cozinha;
import br.com.siberius.siberiusfood.model.Restaurante;
import org.springframework.stereotype.Component;

@Component
public class RestauranteInputDisassembler {

    public Restaurante getRestauranteObject(RestauranteInputDTO restauranteInput) {
        Restaurante restaurante = new Restaurante();
        restaurante.setNome(restauranteInput.getNome());
        restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());

        Cozinha cozinha = new Cozinha();
        cozinha.setId(restauranteInput.getCozinha().getId());

        restaurante.setCozinha(cozinha);

        return restaurante;
    }
}
