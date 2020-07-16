package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.controller.CidadeController;
import br.com.siberius.siberiusfood.api.controller.EstadoController;
import br.com.siberius.siberiusfood.api.model.CidadeDTO;
import br.com.siberius.siberiusfood.model.Cidade;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class CidadeDTOAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeDTO> {

    @Autowired
    private ModelMapper modelMapper;

    public CidadeDTOAssembler() {
        super(CidadeController.class, CidadeDTO.class);
    }

    public CidadeDTO getCidadeDTO(Cidade cidade) {

        CidadeDTO cidadeDTO = createModelWithId(cidade.getId(), cidade);
        modelMapper.map(cidade, cidadeDTO);

        //        CidadeDTO cidadeDTO = modelMapper.map(cidade, CidadeDTO.class);

        //        cidadeDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class)
        //            .buscar(cidadeDTO.getId())).withSelfRel());

        cidadeDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class)
            .listar()).withRel("cidades"));

        cidadeDTO.getEstado().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstadoController.class)
            .buscar(cidadeDTO.getEstado().getId())).withSelfRel());

        return cidadeDTO;
    }

    //    public List<CidadeDTO> getListCidadeDTO(List<Cidade> cidades) {
    //        return cidades.stream()
    //            .map(cidade -> getCidadeDTO(cidade))
    //            .collect(Collectors.toList());
    //    }

    // Só para trazer o link no final da coleção
    @Override
    public CollectionModel<CidadeDTO> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
            .add(WebMvcLinkBuilder.linkTo(CidadeController.class).withSelfRel());
    }

    @Override
    public CidadeDTO toModel(Cidade cidade) {
        return this.getCidadeDTO(cidade);
    }
}
