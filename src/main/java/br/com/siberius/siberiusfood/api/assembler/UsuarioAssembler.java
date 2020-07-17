package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.controller.UsuarioController;
import br.com.siberius.siberiusfood.api.controller.UsuarioGrupoController;
import br.com.siberius.siberiusfood.api.model.UsuarioDTO;
import br.com.siberius.siberiusfood.model.Usuario;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class UsuarioAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioDTO> {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioAssembler() {
        super(UsuarioController.class, UsuarioDTO.class);
    }

    @Override
    public UsuarioDTO toModel(Usuario usuario) {

        UsuarioDTO usuarioDTO = createModelWithId(usuario.getId(), usuario);
        modelMapper.map(usuario, usuarioDTO);

        usuarioDTO.add(WebMvcLinkBuilder.linkTo(UsuarioController.class).withRel("usuarios"));

        usuarioDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioGrupoController.class)
            .lista(usuario.getId())).withRel("grupos-usuario"));

        return usuarioDTO;

//        return modelMapper.map(usuario, UsuarioDTO.class);
    }

//    public List<UsuarioDTO> getListUsuarioDTO(Collection<Usuario> usuarios) {
//        return usuarios.stream()
//            .map(usuario -> getUsuarioDTO(usuario))
//            .collect(Collectors.toList());
//    }

    @Override
    public CollectionModel<UsuarioDTO> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities)
            .add(WebMvcLinkBuilder.linkTo(UsuarioController.class).withSelfRel());
    }
}
