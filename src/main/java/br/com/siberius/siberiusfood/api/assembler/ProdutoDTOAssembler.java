package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.SiberiusLinks;
import br.com.siberius.siberiusfood.api.controller.RestauranteProdutoController;
import br.com.siberius.siberiusfood.api.model.ProdutoDTO;
import br.com.siberius.siberiusfood.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProdutoDTOAssembler
    extends RepresentationModelAssemblerSupport<Produto, ProdutoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SiberiusLinks siberiusLinks;

    public ProdutoDTOAssembler() {
        super(RestauranteProdutoController.class, ProdutoDTO.class);
    }

    @Override
    public ProdutoDTO toModel(Produto produto) {
        ProdutoDTO produtoDTO = createModelWithId(
            produto.getId(), produto, produto.getRestaurante().getId());

        modelMapper.map(produto, produtoDTO);

        produtoDTO.add(siberiusLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));

        return produtoDTO;
    }
//    public ProdutoDTO getProdutoDTO(Produto produto) {
//        return modelMapper.map(produto, ProdutoDTO.class);
//    }

//    public List<ProdutoDTO> getListProdutoDTO(List<Produto> produtos) {
//        return produtos.stream()
//                .map(produto -> getProdutoDTO(produto))
//                .collect(Collectors.toList());
//    }
}
