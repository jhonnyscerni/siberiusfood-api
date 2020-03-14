package br.com.siberius.siberiusfood.api.controller;

import br.com.siberius.siberiusfood.api.assembler.UsuarioAssembler;
import br.com.siberius.siberiusfood.api.assembler.UsuarioDisassembler;
import br.com.siberius.siberiusfood.api.model.UsuarioDTO;
import br.com.siberius.siberiusfood.api.model.input.SenhaInputDTO;
import br.com.siberius.siberiusfood.api.model.input.UsuarioComSenhaDTO;
import br.com.siberius.siberiusfood.api.model.input.UsuarioInputDTO;
import br.com.siberius.siberiusfood.model.Usuario;
import br.com.siberius.siberiusfood.repository.UsuarioRepository;
import br.com.siberius.siberiusfood.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioAssembler assembler;

    @Autowired
    private UsuarioDisassembler disassembler;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioDTO> listar() {
        return assembler.getListUsuarioDTO(usuarioRepository.findAll());
    }

    @GetMapping("/{usuarioId}")
    public UsuarioDTO buscar(@PathVariable Long usuarioId) {
        return assembler.getUsuarioDTO(usuarioService.buscarOuFalhar(usuarioId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO salvar(@RequestBody @Valid UsuarioComSenhaDTO usuarioComSenhaDTO) {
        Usuario usuario = usuarioService.salvar(disassembler.getUsuarioObject(usuarioComSenhaDTO));

        return assembler.getUsuarioDTO(usuario);
    }

    @PutMapping("/{usuarioId}")
    public UsuarioDTO atualizar(@PathVariable Long usuarioId,
                                @RequestBody @Valid UsuarioInputDTO usuarioInputDTO) {
        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
        disassembler.copyToDomainObject(usuarioInputDTO, usuario);

        return assembler.getUsuarioDTO(usuarioService.salvar(usuario));
    }

    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long usuarioId,
                             @RequestBody SenhaInputDTO senhaInputDTO) {
        usuarioService.alterarSenha(usuarioId, senhaInputDTO.getSenhaAtual(), senhaInputDTO.getNovaSenha());
    }


    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long usuarioId) {
        usuarioService.excluir(usuarioId);
    }

}
