package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.SiberiusLinks;
import br.com.siberius.siberiusfood.api.controller.RestauranteProdutoFotoController;
import br.com.siberius.siberiusfood.api.model.FotoProdutoDTO;
import br.com.siberius.siberiusfood.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoDTOAssembler
    extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoDTO> {

    public FotoProdutoDTOAssembler() {
        super(RestauranteProdutoFotoController.class, FotoProdutoDTO.class);
    }

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SiberiusLinks siberiusLinks;

//    public FotoProdutoDTO getCidadeDTO(FotoProduto fotoProduto) {
//        return modelMapper.map(fotoProduto, FotoProdutoDTO.class);
//    }

    @Override
    public FotoProdutoDTO toModel(FotoProduto foto) {
        FotoProdutoDTO fotoProdutoDTO = modelMapper.map(foto, FotoProdutoDTO.class);

        fotoProdutoDTO.add(siberiusLinks.linkToFotoProduto(
            foto.getRestauranteId(), foto.getProduto().getId()));

        fotoProdutoDTO.add(siberiusLinks.linkToProduto(
            foto.getRestauranteId(), foto.getProduto().getId(), "produto"));

        return fotoProdutoDTO;
    }

}
