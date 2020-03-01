package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.model.CidadeDTO;
import br.com.siberius.siberiusfood.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CidadeDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CidadeDTO getCidadeDTO(Cidade cidade) {
        return modelMapper.map(cidade, CidadeDTO.class);
    }

    public List<CidadeDTO> getListCidadeDTO(List<Cidade> cidades) {
        return cidades.stream()
                .map(cidade -> getCidadeDTO(cidade))
                .collect(Collectors.toList());
    }
}
