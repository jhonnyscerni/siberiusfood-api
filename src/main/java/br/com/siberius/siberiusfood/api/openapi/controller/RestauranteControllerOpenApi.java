package br.com.siberius.siberiusfood.api.openapi.controller;

import br.com.siberius.siberiusfood.api.exceptionhandler.Problem;
import br.com.siberius.siberiusfood.api.model.RestauranteDTO;
import br.com.siberius.siberiusfood.api.model.input.RestauranteInputDTO;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

    //    Usando JsonView

//    @ApiOperation(value = "Lista restaurantes", response = RestauranteBasicoModelOpenApi.class)
//    @ApiImplicitParams({
//        @ApiImplicitParam(value = "Nome da projeção de pedidos", allowableValues = "apenas-nome",
//            name = "projecao", paramType = "query", type = "string")
//    })
//    @JsonView(RestauranteView.Resumo.class)
//    public List<RestauranteModel> listar();
//
//    @ApiOperation(value = "Lista restaurantes", hidden = true)
//    public List<RestauranteModel> listarApenasNomes();

    @ApiOperation("Lista restaurantes")
    public List<RestauranteDTO> listar();

    @ApiOperation("Busca um restaurante por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    public RestauranteDTO buscar(
        @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId);

    @ApiOperation("Cadastra um restaurante")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Restaurante cadastrado"),
    })
    public RestauranteDTO salvar(
        @ApiParam(name = "corpo", value = "Representação de um novo restaurante", required = true)
            RestauranteInputDTO restauranteInputDTO);

    @ApiOperation("Atualiza um restaurante por ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Restaurante atualizado"),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    public RestauranteDTO atualizar(
        @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId,

        @ApiParam(name = "corpo", value = "Representação de um restaurante com os novos dados",
            required = true)
            RestauranteInputDTO restauranteInputDTO);

    @ApiOperation("Ativa um restaurante por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Restaurante ativado com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    public void ativar(
        @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId);

    @ApiOperation("Inativa um restaurante por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Restaurante inativado com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    public void inativar(
        @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId);

    @ApiOperation("Ativa múltiplos restaurantes")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Restaurantes ativados com sucesso")
    })
    public void ativarMultiplos(
        @ApiParam(name = "corpo", value = "IDs de restaurantes", required = true)
            List<Long> restauranteIds);

    @ApiOperation("Inativa múltiplos restaurantes")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Restaurantes ativados com sucesso")
    })
    public void inativarMultiplos(
        @ApiParam(name = "corpo", value = "IDs de restaurantes", required = true)
            List<Long> restauranteIds);

    @ApiOperation("Abre um restaurante por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Restaurante aberto com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    public void abertura(
        @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId);

    @ApiOperation("Fecha um restaurante por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Restaurante fechado com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    public void fechamento(
        @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId);

}