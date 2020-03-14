package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.model.input.PermissaoInputDTO;
import br.com.siberius.siberiusfood.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PermissaoDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Permissao getPermissaoObject(PermissaoInputDTO permissaoInputDTO) {
        return modelMapper.map(permissaoInputDTO, Permissao.class);
    }

    public void copyToDomainObject(PermissaoInputDTO permissaoInputDTO, Permissao permissao) {
        modelMapper.map(permissaoInputDTO, permissao);
    }
}
