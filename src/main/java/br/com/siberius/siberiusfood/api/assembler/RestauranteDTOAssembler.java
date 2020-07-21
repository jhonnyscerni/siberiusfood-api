package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.SiberiusLinks;
import br.com.siberius.siberiusfood.api.controller.RestauranteController;
import br.com.siberius.siberiusfood.api.model.RestauranteDTO;
import br.com.siberius.siberiusfood.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteDTOAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SiberiusLinks siberiusLinks;

//    public RestauranteDTO getRestauranteDTO(Restaurante restaurante) {
//        return modelMapper.map(restaurante, RestauranteDTO.class);
//
//    }
//
//    public List<RestauranteDTO> getListRestauranteDTO(List<Restaurante> restaurantes) {
//        return restaurantes.stream()
//            .map(restaurante -> getRestauranteDTO(restaurante))
//            .collect(Collectors.toList());
//    }


    @Override
    public RestauranteDTO toModel(Restaurante restaurante) {
        RestauranteDTO restauranteDTO = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteDTO);

        restauranteDTO.add(siberiusLinks.linkToRestaurantes("restaurantes"));

        restauranteDTO.getCozinha().add(
            siberiusLinks.linkToCozinha(restaurante.getCozinha().getId()));

        restauranteDTO.getEndereco().getCidade().add(
            siberiusLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));

        restauranteDTO.add(siberiusLinks.linkToRestauranteFormasPagamento(restaurante.getId(),
            "formas-pagamento"));

        restauranteDTO.add(siberiusLinks.linkToRestauranteResponsaveis(restaurante.getId(),
            "responsaveis"));

        return restauranteDTO;
    }

    @Override
    public CollectionModel<RestauranteDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
            .add(siberiusLinks.linkToRestaurantes());
    }

    public RestauranteDTOAssembler() {
        super(RestauranteController.class, RestauranteDTO.class);
    }
}
