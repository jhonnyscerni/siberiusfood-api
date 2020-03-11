package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.model.input.GrupoInputDTO;
import br.com.siberius.siberiusfood.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrupoDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Grupo getGrupoObject(GrupoInputDTO grupoInputDTO){
        return modelMapper.map(grupoInputDTO, Grupo.class);
    }

    public void toCopyDomainObjet(GrupoInputDTO grupoInputDTO, Grupo grupo){
        modelMapper.map(grupoInputDTO, grupo);
    }
}
