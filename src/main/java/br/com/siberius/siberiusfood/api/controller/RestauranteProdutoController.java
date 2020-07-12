package br.com.siberius.siberiusfood.api.controller;

import br.com.siberius.siberiusfood.api.assembler.ProdutoDTOAssembler;
import br.com.siberius.siberiusfood.api.assembler.ProdutoDTODisassembler;
import br.com.siberius.siberiusfood.api.model.ProdutoDTO;
import br.com.siberius.siberiusfood.api.model.input.ProdutoInputDTO;
import br.com.siberius.siberiusfood.api.openapi.controller.RestauranteProdutoControllerOpenApi;
import br.com.siberius.siberiusfood.model.Produto;
import br.com.siberius.siberiusfood.model.Restaurante;
import br.com.siberius.siberiusfood.repository.ProdutoRepository;
import br.com.siberius.siberiusfood.service.ProdutoService;
import br.com.siberius.siberiusfood.service.RestauranteService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private ProdutoDTOAssembler assembler;

    @Autowired
    private ProdutoDTODisassembler disassembler;

    @GetMapping
    public List<ProdutoDTO> listar(@PathVariable Long restauranteId, @RequestParam(required = false) boolean incluirInativos) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        List<Produto> todosProdutos = new ArrayList<>();
        if (incluirInativos == true) {
            todosProdutos = produtoRepository.findByRestaurante(restaurante);
        } else {
            todosProdutos = produtoRepository.findByAtivosByRestaurante(restaurante);
        }

        return assembler.getListProdutoDTO(todosProdutos);
    }

    @GetMapping("/{produtoId}")
    public ProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);

        Produto produto = produtoService.buscarOuFalhar(restauranteId, produtoId);

        return assembler.getProdutoDTO(produto);
    }

    @PostMapping
    public ProdutoDTO salvar(@PathVariable Long restauranteId,
        @RequestBody @Valid ProdutoInputDTO produtoInputDTO) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);

        Produto produto = disassembler.getProdutoObject(produtoInputDTO);
        produto.setRestaurante(restaurante);

        return assembler.getProdutoDTO(produtoService.salvar(produto));
    }

    @PutMapping("/{produtoId}")
    public ProdutoDTO atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
        @RequestBody @Valid ProdutoInputDTO produtoInputDTO) {

        Produto produto = produtoService.buscarOuFalhar(restauranteId, produtoId);

        disassembler.copyToDomainObject(produtoInputDTO, produto);

        return assembler.getProdutoDTO(produtoService.salvar(produto));
    }

}
