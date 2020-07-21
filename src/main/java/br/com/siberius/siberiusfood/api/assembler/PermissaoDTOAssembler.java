package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.SiberiusLinks;
import br.com.siberius.siberiusfood.api.model.PermissaoDTO;
import br.com.siberius.siberiusfood.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PermissaoDTOAssembler
    implements RepresentationModelAssembler<Permissao, PermissaoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SiberiusLinks siberiusLinks;

    @Override
    public PermissaoDTO toModel(Permissao permissao) {
        PermissaoDTO permissaoModel = modelMapper.map(permissao, PermissaoDTO.class);
        return permissaoModel;
    }

    @Override
    public CollectionModel<PermissaoDTO> toCollectionModel(Iterable<? extends Permissao> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
            .add(siberiusLinks.linkToPermissoes());
    }

//    public PermissaoDTO getPermissaoDTO(Permissao permissao) {
//        return modelMapper.map(permissao, PermissaoDTO.class);
//    }
//
//    public List<PermissaoDTO> getListPermissaoDTO(Collection<Permissao> permissaos) {
//        return permissaos.stream()
//                .map(permissao -> getPermissaoDTO(permissao))
//                .collect(Collectors.toList());
//    }
}
