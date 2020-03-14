package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.model.PermissaoDTO;
import br.com.siberius.siberiusfood.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissaoDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PermissaoDTO getPermissaoDTO(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoDTO.class);
    }

    public List<PermissaoDTO> getListPermissaoDTO(Collection<Permissao> permissaos) {
        return permissaos.stream()
                .map(permissao -> getPermissaoDTO(permissao))
                .collect(Collectors.toList());
    }
}
