package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.SiberiusLinks;
import br.com.siberius.siberiusfood.api.controller.GrupoController;
import br.com.siberius.siberiusfood.api.model.GrupoDTO;
import br.com.siberius.siberiusfood.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class GrupoDTOAssembler
    extends RepresentationModelAssemblerSupport<Grupo, GrupoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SiberiusLinks siberiusLinks;

    public GrupoDTOAssembler() {
        super(GrupoController.class, GrupoDTO.class);
    }

    @Override
    public GrupoDTO toModel(Grupo grupo) {
        GrupoDTO grupoDTO = createModelWithId(grupo.getId(), grupo);
        modelMapper.map(grupo, grupoDTO);

        grupoDTO.add(siberiusLinks.linkToGrupos("grupos"));

        grupoDTO.add(siberiusLinks.linkToGrupoPermissoes(grupo.getId(), "permissoes"));

        return grupoDTO;
    }

    @Override
    public CollectionModel<GrupoDTO> toCollectionModel(Iterable<? extends Grupo> entities) {
        return super.toCollectionModel(entities)
            .add(siberiusLinks.linkToGrupos());
    }

//    public GrupoDTO getGrupoDTO(Grupo grupo){
//        return modelMapper.map(grupo, GrupoDTO.class);
//    }
//
//    public List<GrupoDTO> getListGrupoDTO(Collection<Grupo> grupos){
//        return grupos.stream()
//                .map(grupo -> getGrupoDTO(grupo))
//                .collect(Collectors.toList());
//    }
}
