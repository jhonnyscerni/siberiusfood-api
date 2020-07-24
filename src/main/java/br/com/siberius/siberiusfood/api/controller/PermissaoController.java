package br.com.siberius.siberiusfood.api.controller;

import br.com.siberius.siberiusfood.api.assembler.PermissaoDTOAssembler;
import br.com.siberius.siberiusfood.api.assembler.PermissaoDTODisassembler;
import br.com.siberius.siberiusfood.api.model.PermissaoDTO;
import br.com.siberius.siberiusfood.api.model.input.PermissaoInputDTO;
import br.com.siberius.siberiusfood.api.openapi.controller.PermissaoControllerOpenApi;
import br.com.siberius.siberiusfood.model.Permissao;
import br.com.siberius.siberiusfood.repository.PermissaoRepository;
import br.com.siberius.siberiusfood.service.PermissaoService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController implements PermissaoControllerOpenApi {

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Autowired
    private PermissaoDTOAssembler assembler;

    @Autowired
    private PermissaoDTODisassembler disassembler;

    @Autowired
    private PermissaoService permissaoService;

    @GetMapping
    public CollectionModel<PermissaoDTO> listar() {
        return assembler.toCollectionModel(permissaoRepository.findAll());
    }

    @GetMapping("/{permissaoId}")
    public PermissaoDTO buscar(@PathVariable Long permissaoId) {
        return assembler.toModel(permissaoService.buscarOuFalhar(permissaoId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PermissaoDTO salvar(@RequestBody @Valid PermissaoInputDTO permissaoInputDTO) {
        Permissao permissao = disassembler.getPermissaoObject(permissaoInputDTO);

        return assembler.toModel(permissaoService.salvar(permissao));

    }

    @PutMapping("/{permissaoId}")
    public PermissaoDTO atualizar(@PathVariable Long permissaoId, @RequestBody PermissaoInputDTO permissaoInputDTO) {
        Permissao permissao = permissaoService.buscarOuFalhar(permissaoId);

        disassembler.copyToDomainObject(permissaoInputDTO, permissao);

        return assembler.toModel(permissaoService.salvar(permissao));

    }
}
