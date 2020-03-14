package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.model.GrupoDTO;
import br.com.siberius.siberiusfood.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GrupoDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public GrupoDTO getGrupoDTO(Grupo grupo){
        return modelMapper.map(grupo, GrupoDTO.class);
    }

    public List<GrupoDTO> getListGrupoDTO(Collection<Grupo> grupos){
        return grupos.stream()
                .map(grupo -> getGrupoDTO(grupo))
                .collect(Collectors.toList());
    }
}
