package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.model.CozinhaDTO;
import br.com.siberius.siberiusfood.api.model.input.CozinhaInputDTO;
import br.com.siberius.siberiusfood.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CozinhaInputDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Cozinha getCozinhaObject(CozinhaInputDTO cozinhaInputDTO){
       return modelMapper.map(cozinhaInputDTO, Cozinha.class);
    }

    public void copyToDomainObject(CozinhaInputDTO cozinhaInputDTO, Cozinha cozinha) {
        modelMapper.map(cozinhaInputDTO, cozinha);
    }
}
