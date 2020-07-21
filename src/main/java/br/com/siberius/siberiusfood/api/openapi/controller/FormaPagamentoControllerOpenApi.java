package br.com.siberius.siberiusfood.api.openapi.controller;

import br.com.siberius.siberiusfood.api.exceptionhandler.Problem;
import br.com.siberius.siberiusfood.api.model.FormaPagamentoDTO;
import br.com.siberius.siberiusfood.api.model.input.FormaPagamentoInputDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

@Api(tags = "Formas de pagamento")
public interface FormaPagamentoControllerOpenApi {

    @ApiOperation("Lista as formas de pagamento")
    ResponseEntity<CollectionModel<FormaPagamentoDTO>> listar(ServletWebRequest request);

    @ApiOperation("Busca uma forma de pagamento por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID da forma de pagamento inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
    })
    ResponseEntity<FormaPagamentoDTO> buscar(
        @ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true)
            Long formaPagamentoId,

        ServletWebRequest request);

    @ApiOperation("Cadastra uma forma de pagamento")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Forma de pagamento cadastrada"),
    })
    FormaPagamentoDTO salvar(
        @ApiParam(name = "corpo", value = "Representação de uma nova forma de pagamento", required = true)
            FormaPagamentoInputDTO formaPagamentoInputDTO);

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Forma de pagamento atualizada"),
        @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
    })
    FormaPagamentoDTO atualizar(
        @ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true)
            Long formaPagamentoId,

        @ApiParam(name = "corpo", value = "Representação de uma forma de pagamento com os novos dados", required = true)
            FormaPagamentoInputDTO formaPagamentoInputDTO);

    @ApiOperation("Exclui uma forma de pagamento por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Forma de pagamento excluída"),
        @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
    })
    void excluir(
        @ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true)
            Long formaPagamentoId);
}