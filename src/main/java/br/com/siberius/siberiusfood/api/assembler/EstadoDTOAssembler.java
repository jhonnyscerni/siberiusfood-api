package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.model.EstadoDTO;
import br.com.siberius.siberiusfood.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstadoDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public EstadoDTO getEstadoDTO(Estado estado){
        return modelMapper.map(estado, EstadoDTO.class);
    }

    public List<EstadoDTO> getListEstadoDTO(List<Estado> estados){
        return estados.stream()
                .map(estado -> getEstadoDTO(estado))
                .collect(Collectors.toList());
    }
}
