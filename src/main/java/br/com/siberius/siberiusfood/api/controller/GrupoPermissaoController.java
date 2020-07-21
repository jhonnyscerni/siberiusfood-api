package br.com.siberius.siberiusfood.api.controller;

import br.com.siberius.siberiusfood.api.SiberiusLinks;
import br.com.siberius.siberiusfood.api.assembler.PermissaoDTOAssembler;
import br.com.siberius.siberiusfood.api.model.PermissaoDTO;
import br.com.siberius.siberiusfood.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import br.com.siberius.siberiusfood.model.Grupo;
import br.com.siberius.siberiusfood.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/grupos/{grupoId}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private PermissaoDTOAssembler assembler;

    @Autowired
    private SiberiusLinks siberiusLinks;

    @Override
    @GetMapping
    public CollectionModel<PermissaoDTO> listar(@PathVariable Long grupoId) {
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);

        CollectionModel<PermissaoDTO> permissoesModel
            = assembler.toCollectionModel(grupo.getPermissoes())
            .removeLinks()
            .add(siberiusLinks.linkToGrupoPermissoes(grupoId))
            .add(siberiusLinks.linkToGrupoPermissaoAssociacao(grupoId, "associar"));

        permissoesModel.getContent().forEach(permissaoModel -> {
            permissaoModel.add(siberiusLinks.linkToGrupoPermissaoDesassociacao(
                grupoId, permissaoModel.getId(), "desassociar"));
        });

        return permissoesModel;
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long grupoId,
        @PathVariable Long permissaoId) {
        grupoService.associarPermissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long grupoId,
        @PathVariable Long permissaoId) {
        grupoService.desassociarPermissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();
    }
}
