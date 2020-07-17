package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.controller.EstadoController;
import br.com.siberius.siberiusfood.api.model.EstadoDTO;
import br.com.siberius.siberiusfood.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstadoDTOAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    public EstadoDTOAssembler() {
        super(EstadoController.class, EstadoDTO.class);
    }

    @Override
    public EstadoDTO toModel(Estado estado) {
        EstadoDTO estad = createModelWithId(estado.getId(), estado);
        modelMapper.map(estado, estad);

        estad.add(WebMvcLinkBuilder.linkTo(EstadoController.class).withRel("estados"));

        return estad;
//        return modelMapper.map(estado, EstadoDTO.class);
    }

    @Override
    public CollectionModel<EstadoDTO> toCollectionModel(Iterable<? extends Estado> entities) {
        return super.toCollectionModel(entities)
            .add(WebMvcLinkBuilder.linkTo(EstadoController.class).withSelfRel());
    }

//    public List<EstadoDTO> getListEstadoDTO(List<Estado> estados) {
//        return estados.stream()
//            .map(estado -> getEstadoDTO(estado))
//            .collect(Collectors.toList());
//    }

}
