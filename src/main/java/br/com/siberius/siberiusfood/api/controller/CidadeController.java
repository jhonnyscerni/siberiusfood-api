package br.com.siberius.siberiusfood.api.controller;

import br.com.siberius.siberiusfood.api.assembler.CidadeDTOAssembler;
import br.com.siberius.siberiusfood.api.assembler.CidadeInputDTODisassembler;
import br.com.siberius.siberiusfood.api.exceptionhandler.Problem;
import br.com.siberius.siberiusfood.api.model.CidadeDTO;
import br.com.siberius.siberiusfood.api.model.input.CidadeInputDTO;
import br.com.siberius.siberiusfood.exception.EstadoNaoEncontradoException;
import br.com.siberius.siberiusfood.exception.NegocioException;
import br.com.siberius.siberiusfood.model.Cidade;
import br.com.siberius.siberiusfood.repository.CidadeRepository;
import br.com.siberius.siberiusfood.service.CidadeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Cidades")
@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private CidadeDTOAssembler assembler;

    @Autowired
    private CidadeInputDTODisassembler disassembler;

    @ApiOperation("Lista as cidades")
    @GetMapping
    public List<CidadeDTO> listar() {
        return assembler.getListCidadeDTO(cidadeRepository.findAll());
    }

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    @GetMapping("/{cidadeId}")
    public CidadeDTO buscar(
        @ApiParam(value = "ID de uma cidade", example = "1")
        @PathVariable Long cidadeId) {
        return assembler.getCidadeDTO(cidadeService.buscarOuFalhar(cidadeId));
    }

    @ApiOperation("Cadastra uma cidade")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Cidade cadastrada"),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDTO salvar(
        @ApiParam(name = "corpo", value = "Representação de uma nova cidade") @RequestBody @Valid CidadeInputDTO cidadeInputDTO) {
        try {
            Cidade cidade = disassembler.getCidadeObject(cidadeInputDTO);

            return assembler.getCidadeDTO(cidadeService.salvar(cidade));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Cidade atualizada"),
        @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    @PutMapping("/{cidadeId}")
    public CidadeDTO atualizar(
        @ApiParam(value = "ID de uma cidade", example = "1")
        @PathVariable Long cidadeId,
        @ApiParam(name = "corpo", value = "Representação de uma cidade com os dados atualizados")
        @RequestBody @Valid CidadeInputDTO cidadeInputDTO) {
        try {

            Cidade cidadeAtual = cidadeService.buscarOuFalhar(cidadeId);

            disassembler.copyToDomainObject(cidadeInputDTO, cidadeAtual);

            return assembler.getCidadeDTO(cidadeService.salvar(cidadeAtual));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @ApiOperation("Exclui uma cidade por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Cidade excluída"),
        @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(
        @ApiParam(value = "ID de uma cidade", example = "1")
        @PathVariable Long cidadeId) {
        cidadeService.excluir(cidadeId);
    }

}
