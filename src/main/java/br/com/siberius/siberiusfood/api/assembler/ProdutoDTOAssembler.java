package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.model.ProdutoDTO;
import br.com.siberius.siberiusfood.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public ProdutoDTO getProdutoDTO(Produto produto) {
        return modelMapper.map(produto, ProdutoDTO.class);
    }

    public List<ProdutoDTO> getListProdutoDTO(List<Produto> produtos) {
        return produtos.stream()
                .map(produto -> getProdutoDTO(produto))
                .collect(Collectors.toList());
    }
}
