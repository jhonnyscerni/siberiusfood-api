package br.com.siberius.siberiusfood.api.controller;

import br.com.siberius.siberiusfood.api.ResourceUriHelper;
import br.com.siberius.siberiusfood.api.assembler.CidadeDTOAssembler;
import br.com.siberius.siberiusfood.api.assembler.CidadeInputDTODisassembler;
import br.com.siberius.siberiusfood.api.model.CidadeDTO;
import br.com.siberius.siberiusfood.api.model.input.CidadeInputDTO;
import br.com.siberius.siberiusfood.api.openapi.controller.CidadeControllerOpenApi;
import br.com.siberius.siberiusfood.exception.EstadoNaoEncontradoException;
import br.com.siberius.siberiusfood.exception.NegocioException;
import br.com.siberius.siberiusfood.model.Cidade;
import br.com.siberius.siberiusfood.repository.CidadeRepository;
import br.com.siberius.siberiusfood.service.CidadeService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private CidadeDTOAssembler assembler;

    @Autowired
    private CidadeInputDTODisassembler disassembler;

    @GetMapping
    public CollectionModel<CidadeDTO> listar() {
        return assembler.toCollectionModel(cidadeRepository.findAll());
    }

    @GetMapping("/{cidadeId}")
    public CidadeDTO buscar(
        @PathVariable Long cidadeId) {
        return assembler.getCidadeDTO(cidadeService.buscarOuFalhar(cidadeId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDTO salvar(
        @RequestBody @Valid CidadeInputDTO cidadeInputDTO) {
        try {
            Cidade cidade = disassembler.getCidadeObject(cidadeInputDTO);

            CidadeDTO cidadeDTO = assembler.getCidadeDTO(cidadeService.salvar(cidade));

            ResourceUriHelper.addUriInResponseHeader(cidadeDTO.getId());

            return cidadeDTO;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{cidadeId}")
    public CidadeDTO atualizar(
        @PathVariable Long cidadeId,
        @RequestBody @Valid CidadeInputDTO cidadeInputDTO) {
        try {

            Cidade cidadeAtual = cidadeService.buscarOuFalhar(cidadeId);

            disassembler.copyToDomainObject(cidadeInputDTO, cidadeAtual);

            return assembler.getCidadeDTO(cidadeService.salvar(cidadeAtual));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(
        @PathVariable Long cidadeId) {
        cidadeService.excluir(cidadeId);
    }

}
