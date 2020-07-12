package br.com.siberius.siberiusfood.api.openapi.controller;

import br.com.siberius.siberiusfood.api.exceptionhandler.Problem;
import br.com.siberius.siberiusfood.api.model.PedidoDTO;
import br.com.siberius.siberiusfood.api.model.PedidoResumoDTO;
import br.com.siberius.siberiusfood.api.model.input.PedidoInputDTO;
import br.com.siberius.siberiusfood.filter.PedidoFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {

    @ApiImplicitParams({
        @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
            name = "campos", paramType = "query", type = "string")
    })
    @ApiOperation("Pesquisa os pedidos")
    public Page<PedidoResumoDTO> pesquisar(PedidoFilter filtro, Pageable pageable);

    @ApiOperation("Registra um pedido")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Pedido registrado"),
    })
    public PedidoDTO salvar(
        @ApiParam(name = "corpo", value = "Representação de um novo pedido", required = true)
            PedidoInputDTO pedidoInputDTO);

    @ApiImplicitParams({
        @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
            name = "campos", paramType = "query", type = "string")
    })
    @ApiOperation("Busca um pedido por código")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    public PedidoDTO buscar(
        @ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true)
            String codigoPedido);
}