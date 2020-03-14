package br.com.siberius.siberiusfood.api.controller;

import br.com.siberius.siberiusfood.api.assembler.PermissaoDTOAssembler;
import br.com.siberius.siberiusfood.api.assembler.PermissaoDTODisassembler;
import br.com.siberius.siberiusfood.api.model.PermissaoDTO;
import br.com.siberius.siberiusfood.api.model.input.PermissaoInputDTO;
import br.com.siberius.siberiusfood.model.Permissao;
import br.com.siberius.siberiusfood.repository.PermissaoRepository;
import br.com.siberius.siberiusfood.service.PermissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/permissoes")
public class PermissaoController {

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Autowired
    private PermissaoDTOAssembler assembler;

    @Autowired
    private PermissaoDTODisassembler disassembler;

    @Autowired
    private PermissaoService permissaoService;

    @GetMapping
    public List<PermissaoDTO> listar() {
        return assembler.getListPermissaoDTO(permissaoRepository.findAll());
    }

    @GetMapping("/{permissaoId}")
    public PermissaoDTO buscar(@PathVariable Long permissaoId) {
        return assembler.getPermissaoDTO(permissaoService.buscarOuFalhar(permissaoId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PermissaoDTO salvar(@RequestBody @Valid PermissaoInputDTO permissaoInputDTO) {
        Permissao permissao = disassembler.getPermissaoObject(permissaoInputDTO);

        return assembler.getPermissaoDTO(permissaoService.salvar(permissao));

    }

    @PutMapping("/{permissaoId}")
    public PermissaoDTO atualizar(@PathVariable Long permissaoId, @RequestBody PermissaoInputDTO permissaoInputDTO) {
        Permissao permissao = permissaoService.buscarOuFalhar(permissaoId);

        disassembler.copyToDomainObject(permissaoInputDTO, permissao);

        return assembler.getPermissaoDTO(permissaoService.salvar(permissao));

    }
}
