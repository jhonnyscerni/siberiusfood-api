package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.SiberiusLinks;
import br.com.siberius.siberiusfood.api.controller.CidadeController;
import br.com.siberius.siberiusfood.api.model.CidadeDTO;
import br.com.siberius.siberiusfood.model.Cidade;
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

    @Autowired
    private SiberiusLinks siberiusLinks;

    public CidadeDTOAssembler() {
        super(CidadeController.class, CidadeDTO.class);
    }

    public CidadeDTO getCidadeDTO(Cidade cidade) {

        CidadeDTO cidadeDTO = createModelWithId(cidade.getId(), cidade);
        modelMapper.map(cidade, cidadeDTO);

        cidadeDTO.add(siberiusLinks.linkToCidades("cidades"));

        cidadeDTO.getEstado().add(siberiusLinks.linkToEstado(cidadeDTO.getEstado().getId()));

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
            .add(siberiusLinks.linkToCidades());
    }

    @Override
    public CidadeDTO toModel(Cidade cidade) {
        return this.getCidadeDTO(cidade);
    }
}
