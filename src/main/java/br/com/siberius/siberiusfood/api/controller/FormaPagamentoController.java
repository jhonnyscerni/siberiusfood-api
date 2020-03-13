package br.com.siberius.siberiusfood.api.controller;

import br.com.siberius.siberiusfood.api.assembler.FormaPagamentoDTOAssembler;
import br.com.siberius.siberiusfood.api.assembler.FormaPagamentoDTODisassembler;
import br.com.siberius.siberiusfood.api.model.FormaPagamentoDTO;
import br.com.siberius.siberiusfood.api.model.input.FormaPagamentoInputDTO;
import br.com.siberius.siberiusfood.model.FormaPagamento;
import br.com.siberius.siberiusfood.repository.FormaPagamentoRepository;
import br.com.siberius.siberiusfood.repository.RestauranteRepository;
import br.com.siberius.siberiusfood.service.FormaPagamentoService;
import br.com.siberius.siberiusfood.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/formas-pagamento")
public class FormaPagamentoController {

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
    public List<FormaPagamentoDTO> listar() {
        return assembler.getListFormaPagamentoDTO(formaPagamentoRepository.findAll());
    }

    @GetMapping("/{formaPagamentoId}")
    public FormaPagamentoDTO buscar(@PathVariable Long formaPagamentoId) {
        return assembler.getFormaPagamentoDTO(formaPagamentoService.buscarOuFalhar(formaPagamentoId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoDTO salvar(@RequestBody @Valid FormaPagamentoInputDTO formaPagamentoInputDTO){
       FormaPagamento formaPagamento =
               formaPagamentoService.salvar(disassembler.getFormaPagamentoObject(formaPagamentoInputDTO));

       return assembler.getFormaPagamentoDTO(formaPagamento) ;
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
                                       @RequestBody @Valid FormaPagamentoInputDTO formaPagamentoInputDTO){

        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);

        disassembler.copyToDomainObject(formaPagamentoInputDTO, formaPagamento);

        return assembler.getFormaPagamentoDTO(formaPagamentoService.salvar(formaPagamento));
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long formaPagamentoId){
        formaPagamentoService.excluir(formaPagamentoId);
    }

}
