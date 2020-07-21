package br.com.siberius.siberiusfood.api.controller;

import br.com.siberius.siberiusfood.api.SiberiusLinks;
import br.com.siberius.siberiusfood.api.assembler.FormaPagamentoDTOAssembler;
import br.com.siberius.siberiusfood.api.model.FormaPagamentoDTO;
import br.com.siberius.siberiusfood.api.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import br.com.siberius.siberiusfood.model.Restaurante;
import br.com.siberius.siberiusfood.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private FormaPagamentoDTOAssembler assembler;

    @Autowired
    private SiberiusLinks siberiusLinks;


    @Override
    @GetMapping
    public CollectionModel<FormaPagamentoDTO> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);

        CollectionModel<FormaPagamentoDTO> formasPagamentoModel
            = assembler.toCollectionModel(restaurante.getFormaPagamentos())
            .removeLinks()
            .add(siberiusLinks.linkToRestauranteFormasPagamento(restauranteId));

        formasPagamentoModel.getContent().forEach(formaPagamentoModel -> {
            formaPagamentoModel.add(siberiusLinks.linkToRestauranteFormaPagamentoDesassociacao(
                restauranteId, formaPagamentoModel.getId(), "desassociar"));
        });

        return formasPagamentoModel;
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId,
        @PathVariable Long formaPagamentoId) {
        restauranteService.desassociarFormarPagamento(restauranteId, formaPagamentoId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{formaPagamentoId}")
    public void associar(@PathVariable Long restauranteId,
        @PathVariable Long formaPagamentoId) {
        restauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);
    }
}
