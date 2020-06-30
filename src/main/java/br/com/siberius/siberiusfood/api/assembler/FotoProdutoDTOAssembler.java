package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.model.FotoProdutoDTO;
import br.com.siberius.siberiusfood.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public FotoProdutoDTO getCidadeDTO(FotoProduto fotoProduto) {
        return modelMapper.map(fotoProduto, FotoProdutoDTO.class);
    }

}
