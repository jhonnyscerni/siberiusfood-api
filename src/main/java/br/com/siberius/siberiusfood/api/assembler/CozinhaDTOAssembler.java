package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.model.CozinhaDTO;
import br.com.siberius.siberiusfood.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CozinhaDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CozinhaDTO getCozinhaDTO(Cozinha cozinha){
       return modelMapper.map(cozinha, CozinhaDTO.class);
    }

    public List<CozinhaDTO> getLisCozinhasDTO(List<Cozinha> cozinhas){
        return cozinhas.stream()
                .map(cozinha -> getCozinhaDTO(cozinha))
                .collect(Collectors.toList());
    }
}
