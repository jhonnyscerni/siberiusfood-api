package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.SiberiusLinks;
import br.com.siberius.siberiusfood.api.controller.RestauranteController;
import br.com.siberius.siberiusfood.api.model.RestauranteBasicoDTO;
import br.com.siberius.siberiusfood.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteBasicoDTOAssembler
    extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SiberiusLinks siberiusLinks;

    public RestauranteBasicoDTOAssembler() {
        super(RestauranteController.class, RestauranteBasicoDTO.class);
    }

    @Override
    public RestauranteBasicoDTO toModel(Restaurante restaurante) {
        RestauranteBasicoDTO restauranteBasicoDTO = createModelWithId(
            restaurante.getId(), restaurante);

        modelMapper.map(restaurante, restauranteBasicoDTO);

        restauranteBasicoDTO.add(siberiusLinks.linkToRestaurantes("restaurantes"));

        restauranteBasicoDTO.getCozinha().add(
            siberiusLinks.linkToCozinha(restaurante.getCozinha().getId()));

        return restauranteBasicoDTO;
    }

    @Override
    public CollectionModel<RestauranteBasicoDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
            .add(siberiusLinks.linkToRestaurantes());
    }
}
