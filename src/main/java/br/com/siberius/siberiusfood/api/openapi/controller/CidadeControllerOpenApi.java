package br.com.siberius.siberiusfood.api.openapi.controller;

import br.com.siberius.siberiusfood.api.exceptionhandler.Problem;
import br.com.siberius.siberiusfood.api.model.CidadeDTO;
import br.com.siberius.siberiusfood.api.model.input.CidadeInputDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

    @ApiOperation("Lista as cidades")
    CollectionModel<CidadeDTO> listar();

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    CidadeDTO buscar(
        @ApiParam(value = "ID de uma cidade", example = "1", required = true)
            Long cidadeId);

    @ApiOperation("Cadastra uma cidade")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Cidade cadastrada"),
    })
    CidadeDTO salvar(
        @ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true)
            CidadeInputDTO cidadeInputDTO);

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Cidade atualizada"),
        @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    CidadeDTO atualizar(
        @ApiParam(value = "ID de uma cidade", example = "1", required = true)
            Long cidadeId,

        @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados")
            CidadeInputDTO cidadeInputDTO);

    @ApiOperation("Exclui uma cidade por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Cidade excluída"),
        @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    void remover(
        @ApiParam(value = "ID de uma cidade", example = "1", required = true)
            Long cidadeId);

}
