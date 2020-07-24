package br.com.siberius.siberiusfood.api.controller;

import br.com.siberius.siberiusfood.api.assembler.GrupoDTOAssembler;
import br.com.siberius.siberiusfood.api.assembler.GrupoDTODisassembler;
import br.com.siberius.siberiusfood.api.model.GrupoDTO;
import br.com.siberius.siberiusfood.api.model.input.GrupoInputDTO;
import br.com.siberius.siberiusfood.api.openapi.controller.GrupoControllerOpenApi;
import br.com.siberius.siberiusfood.model.Grupo;
import br.com.siberius.siberiusfood.repository.GrupoRepository;
import br.com.siberius.siberiusfood.service.GrupoService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private GrupoDTOAssembler assembler;

    @Autowired
    GrupoDTODisassembler disassembler;

    @GetMapping
    public CollectionModel<GrupoDTO> listar() {
        return assembler.toCollectionModel(grupoRepository.findAll());
    }

    @GetMapping("/{grupoId}")
    public GrupoDTO buscar(@PathVariable Long grupoId) {
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);
        return assembler.toModel(grupo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDTO salvar(@RequestBody @Valid GrupoInputDTO grupoInputDTO) {
        return assembler.toModel(
            grupoService.salvar(disassembler.getGrupoObject(grupoInputDTO)));
    }

    @PutMapping("/{grupoId}")
    public GrupoDTO atualizar(@PathVariable Long grupoId,
                              @RequestBody @Valid GrupoInputDTO grupoInputDTO) {
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);
        disassembler.toCopyDomainObjet(grupoInputDTO, grupo);

        return assembler.toModel(grupoService.salvar(grupo));
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long grupoId) {
        grupoService.excluir(grupoId);
    }

}
