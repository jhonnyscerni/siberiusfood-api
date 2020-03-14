package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.model.UsuarioDTO;
import br.com.siberius.siberiusfood.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioDTO getUsuarioDTO(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    public List<UsuarioDTO> getListUsuarioDTO(Collection<Usuario> usuarios) {
        return usuarios.stream()
                .map(usuario -> getUsuarioDTO(usuario))
                .collect(Collectors.toList());
    }
}
