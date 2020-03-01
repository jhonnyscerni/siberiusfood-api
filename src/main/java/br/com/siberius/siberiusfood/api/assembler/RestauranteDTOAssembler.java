package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.model.CozinhaDTO;
import br.com.siberius.siberiusfood.api.model.RestauranteDTO;
import br.com.siberius.siberiusfood.model.Restaurante;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteDTOAssembler {

    public RestauranteDTO getRestauranteDTO(Restaurante restaurante) {
        CozinhaDTO cozinhaDTO = new CozinhaDTO();
        cozinhaDTO.setId(restaurante.getCozinha().getId());
        cozinhaDTO.setNome(restaurante.getCozinha().getNome());

        RestauranteDTO restauranteDTO = new RestauranteDTO();
        restauranteDTO.setId(restaurante.getId());
        restauranteDTO.setNome(restaurante.getNome());
        restauranteDTO.setTaxaFrete(restaurante.getTaxaFrete());
        restauranteDTO.setCozinha(cozinhaDTO);
        return restauranteDTO;
    }

    public List<RestauranteDTO> getListRestauranteDTO(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(restaurante -> getRestauranteDTO(restaurante))
                .collect(Collectors.toList());
    }
}
