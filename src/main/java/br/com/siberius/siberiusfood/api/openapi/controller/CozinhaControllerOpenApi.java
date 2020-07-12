package br.com.siberius.siberiusfood.api.openapi.controller;

import br.com.siberius.siberiusfood.api.exceptionhandler.Problem;
import br.com.siberius.siberiusfood.api.model.CozinhaDTO;
import br.com.siberius.siberiusfood.api.model.input.CozinhaInputDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

    @ApiOperation("Lista as cozinhas com paginação")
    Page<CozinhaDTO> listar(Pageable pageable);

    @ApiOperation("Busca uma cozinha por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    CozinhaDTO buscar(
        @ApiParam(value = "ID de uma cozinha", example = "1")
            Long cozinhaId);

    @ApiOperation("Cadastra uma cozinha")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Cozinha cadastrada"),
    })
    CozinhaDTO adicionar(
        @ApiParam(name = "corpo", value = "Representação de uma nova cozinha")
            CozinhaInputDTO cozinhaInputDTO);

    @ApiOperation("Atualiza uma cozinha por ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Cozinha atualizada"),
        @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    CozinhaDTO atualizar(
        @ApiParam(value = "ID de uma cozinha", example = "1")
            Long cozinhaId,

        @ApiParam(name = "corpo", value = "Representação de uma cozinha com os novos dados")
            CozinhaInputDTO cozinhaInputDTO);

    @ApiOperation("Exclui uma cozinha por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Cozinha excluída"),
        @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    void remover(
        @ApiParam(value = "ID de uma cozinha", example = "1")
            Long cozinhaId);
}