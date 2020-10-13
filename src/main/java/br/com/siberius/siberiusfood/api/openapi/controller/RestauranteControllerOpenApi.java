package br.com.siberius.siberiusfood.api.openapi.controller;

import br.com.siberius.siberiusfood.api.exceptionhandler.Problem;
import br.com.siberius.siberiusfood.api.model.RestauranteBasicoDTO;
import br.com.siberius.siberiusfood.api.model.RestauranteDTO;
import br.com.siberius.siberiusfood.api.model.input.RestauranteInputDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

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

//    @ApiOperation(value = "Lista restaurantes")
//    @ApiImplicitParams({
//        @ApiImplicitParam(value = "Nome da projeção de pedidos", allowableValues = "apenas-nome",
//            name = "projecao", paramType = "query", type = "string")
//    })
//    CollectionModel<RestauranteDTO> listar();

    @ApiOperation("Busca um restaurante por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    RestauranteDTO buscar(
        @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId);

    @ApiOperation("Cadastra um restaurante")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Restaurante cadastrado"),
    })
    RestauranteDTO salvar(
        @ApiParam(name = "corpo", value = "Representação de um novo restaurante", required = true)
            RestauranteInputDTO restauranteInputDTO);

    @ApiOperation("Atualiza um restaurante por ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Restaurante atualizado"),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    RestauranteDTO atualizar(
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
    ResponseEntity<Void> ativar(
        @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId);

    @ApiOperation("Inativa um restaurante por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Restaurante inativado com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> inativar(
        @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId);

    @ApiOperation("Ativa múltiplos restaurantes")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Restaurantes ativados com sucesso")
    })
    void ativarMultiplos(
        @ApiParam(name = "corpo", value = "IDs de restaurantes", required = true)
            List<Long> restauranteIds);

    @ApiOperation("Inativa múltiplos restaurantes")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Restaurantes ativados com sucesso")
    })
    void inativarMultiplos(
        @ApiParam(name = "corpo", value = "IDs de restaurantes", required = true)
            List<Long> restauranteIds);

    @ApiOperation("Abre um restaurante por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Restaurante aberto com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> abertura(
        @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId);

    @ApiOperation("Fecha um restaurante por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Restaurante fechado com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> fechamento(
        @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId);

}