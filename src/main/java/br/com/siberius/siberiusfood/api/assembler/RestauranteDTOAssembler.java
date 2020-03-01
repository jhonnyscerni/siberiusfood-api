package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.model.RestauranteDTO;
import br.com.siberius.siberiusfood.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public RestauranteDTO getRestauranteDTO(Restaurante restaurante) {
        return modelMapper.map(restaurante, RestauranteDTO.class);

    }

    public List<RestauranteDTO> getListRestauranteDTO(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(restaurante -> getRestauranteDTO(restaurante))
                .collect(Collectors.toList());
    }
}
