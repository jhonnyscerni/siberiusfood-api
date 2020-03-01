package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.model.input.RestauranteInputDTO;
import br.com.siberius.siberiusfood.model.Cozinha;
import br.com.siberius.siberiusfood.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Restaurante getRestauranteObject(RestauranteInputDTO restauranteInput) {
        return modelMapper.map(restauranteInput, Restaurante.class);
    }

    public void toCopyDomainObject(RestauranteInputDTO restauranteInputDTO, Restaurante restaurante) {
        // Para evitar org.hibernate.HibernateException: identifier of an instance of
        // com.algaworks.algafood.domain.model.Cozinha was altered from 1 to 2
        restaurante.setCozinha(new Cozinha());
        modelMapper.map(restauranteInputDTO, restaurante);
    }
}
