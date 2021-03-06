package br.com.siberius.siberiusfood.api.controller;

import br.com.siberius.siberiusfood.api.assembler.FormaPagamentoDTOAssembler;
import br.com.siberius.siberiusfood.api.assembler.FormaPagamentoDTODisassembler;
import br.com.siberius.siberiusfood.api.model.FormaPagamentoDTO;
import br.com.siberius.siberiusfood.api.model.input.FormaPagamentoInputDTO;
import br.com.siberius.siberiusfood.api.openapi.controller.FormaPagamentoControllerOpenApi;
import br.com.siberius.siberiusfood.model.FormaPagamento;
import br.com.siberius.siberiusfood.repository.FormaPagamentoRepository;
import br.com.siberius.siberiusfood.repository.RestauranteRepository;
import br.com.siberius.siberiusfood.service.FormaPagamentoService;
import br.com.siberius.siberiusfood.service.RestauranteService;
import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@RestController
@RequestMapping(value = "/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private FormaPagamentoDTOAssembler assembler;

    @Autowired
    private FormaPagamentoDTODisassembler disassembler;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private RestauranteService restauranteService;

    @GetMapping
    public ResponseEntity<CollectionModel<FormaPagamentoDTO>> listar(ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";

        OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();

        if (dataUltimaAtualizacao != null) {
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
        }

        if (request.checkNotModified(eTag)) {
            return null;
        }

        CollectionModel<FormaPagamentoDTO> formasPagamentosDTO = assembler.toCollectionModel(formaPagamentoRepository.findAll());

        return ResponseEntity.ok()
//				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
//				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
            .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
//				.cacheControl(CacheControl.noCache())
//              .cacheControl(CacheControl.noStore())
            .eTag(eTag)
            .body(formasPagamentosDTO);
    }

    @GetMapping("/{formaPagamentoId}")
    public ResponseEntity<FormaPagamentoDTO> buscar(@PathVariable Long formaPagamentoId, ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";

        OffsetDateTime dataAtualizacao = formaPagamentoRepository
            .getDataAtualizacaoById(formaPagamentoId);

        if (dataAtualizacao != null) {
            eTag = String.valueOf(dataAtualizacao.toEpochSecond());
        }

        if (request.checkNotModified(eTag)) {
            return null;
        }

        FormaPagamentoDTO formaPagamentoDTO = assembler.toModel(formaPagamentoService.buscarOuFalhar(formaPagamentoId));

        return ResponseEntity.ok()
            .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
            .eTag(eTag)
            .body(formaPagamentoDTO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoDTO salvar(@RequestBody @Valid FormaPagamentoInputDTO formaPagamentoInputDTO) {
        FormaPagamento formaPagamento =
            formaPagamentoService.salvar(disassembler.getFormaPagamentoObject(formaPagamentoInputDTO));

        return assembler.toModel(formaPagamento);
    }

    //CADASTRAR FORMA DE PAGAMENTO E ADICIONAR ESSA FORMA DE PAGAMENTO A TODOS OS RESTAURANTES

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public FormaPagamentoDTO adicionar(@RequestBody @Valid FormaPagamentoInputDTO formaPagamentoInput) {
//        FormaPagamento formaPagamento = disassembler.getFormaPagamentoObject(formaPagamentoInput);
//
//        formaPagamento = formaPagamentoService.salvar(formaPagamento);
//
//        List<Restaurante> restaurantes = restauranteRepository.findAll();
//
//        for(Restaurante restaurante : restaurantes) {
//            restauranteService.associarFormaPagamento(restaurante.getId(), formaPagamento.getId());
//        }
//
//        return assembler.getFormaPagamentoDTO(formaPagamento);
//    }

    @PutMapping("/{formaPagamentoId}")
    public FormaPagamentoDTO atualizar(@PathVariable Long formaPagamentoId,
        @RequestBody @Valid FormaPagamentoInputDTO formaPagamentoInputDTO) {

        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);

        disassembler.copyToDomainObject(formaPagamentoInputDTO, formaPagamento);

        return assembler.toModel(formaPagamentoService.salvar(formaPagamento));
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long formaPagamentoId) {
        formaPagamentoService.excluir(formaPagamentoId);
    }

}
