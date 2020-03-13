package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.model.ProdutoDTO;
import br.com.siberius.siberiusfood.api.model.input.ProdutoInputDTO;
import br.com.siberius.siberiusfood.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Produto getProdutoObject(ProdutoInputDTO produtoInputDTO) {
        return modelMapper.map(produtoInputDTO, Produto.class);
    }

    public void copyToDomainObject(ProdutoInputDTO produtoInputDTO, Produto produto) {
        modelMapper.map(produtoInputDTO, produto);
    }
}
