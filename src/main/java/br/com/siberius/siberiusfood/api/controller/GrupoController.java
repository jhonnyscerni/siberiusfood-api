package br.com.siberius.siberiusfood.api.controller;

import br.com.siberius.siberiusfood.api.assembler.GrupoDTOAssembler;
import br.com.siberius.siberiusfood.api.assembler.GrupoDTODisassembler;
import br.com.siberius.siberiusfood.api.controller.openapi.GrupoControllerOpenApi;
import br.com.siberius.siberiusfood.api.model.GrupoDTO;
import br.com.siberius.siberiusfood.api.model.input.GrupoInputDTO;
import br.com.siberius.siberiusfood.model.Grupo;
import br.com.siberius.siberiusfood.repository.GrupoRepository;
import br.com.siberius.siberiusfood.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public List<GrupoDTO> listar() {
        return assembler.getListGrupoDTO(grupoRepository.findAll());
    }

    @GetMapping("/{grupoId}")
    public GrupoDTO buscar(@PathVariable Long grupoId) {
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);
        return assembler.getGrupoDTO(grupo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDTO salvar(@RequestBody @Valid GrupoInputDTO grupoInputDTO) {
        return assembler.getGrupoDTO(
                grupoService.salvar(disassembler.getGrupoObject(grupoInputDTO)));
    }

    @PutMapping("/{grupoId}")
    public GrupoDTO atualizar(@PathVariable Long grupoId,
                              @RequestBody @Valid GrupoInputDTO grupoInputDTO) {
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);
        disassembler.toCopyDomainObjet(grupoInputDTO, grupo);

        return assembler.getGrupoDTO(grupoService.salvar(grupo));
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long grupoId) {
        grupoService.excluir(grupoId);
    }

}
