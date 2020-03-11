package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.model.input.UsuarioInputDTO;
import br.com.siberius.siberiusfood.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Usuario getUsuarioObject(UsuarioInputDTO usuarioInputDTO){
       return modelMapper.map(usuarioInputDTO, Usuario.class);
    }

    public void copyToDomainObject(UsuarioInputDTO usuarioInputDTO, Usuario usuario){
        modelMapper.map(usuarioInputDTO, usuario);
    }
}
