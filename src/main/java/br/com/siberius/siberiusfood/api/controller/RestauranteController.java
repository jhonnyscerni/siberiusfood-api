package br.com.siberius.siberiusfood.api.controller;

import br.com.siberius.siberiusfood.api.assembler.RestauranteDTOAssembler;
import br.com.siberius.siberiusfood.api.assembler.RestauranteInputDisassembler;
import br.com.siberius.siberiusfood.api.model.RestauranteDTO;
import br.com.siberius.siberiusfood.api.model.input.RestauranteInputDTO;
import br.com.siberius.siberiusfood.exception.CidadeNaoEncontradaException;
import br.com.siberius.siberiusfood.exception.CozinhaNaoEncontradaException;
import br.com.siberius.siberiusfood.exception.NegocioException;
import br.com.siberius.siberiusfood.model.Restaurante;
import br.com.siberius.siberiusfood.repository.RestauranteRepository;
import br.com.siberius.siberiusfood.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private RestauranteDTOAssembler assembler;

    @Autowired
    private RestauranteInputDisassembler disassembler;

    @GetMapping
    public List<RestauranteDTO> listar() {
        return assembler.getListRestauranteDTO(restauranteRepository.findAll());
    }

    @GetMapping("/{restauranteId}")
    public RestauranteDTO buscar(@PathVariable Long restauranteId) {

        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);

        return assembler.getRestauranteDTO(restaurante);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDTO salvar(@RequestBody @Valid RestauranteInputDTO restauranteInputDTO) {
        try {
            Restaurante restaurante = disassembler.getRestauranteObject(restauranteInputDTO);

            return assembler.getRestauranteDTO(restauranteService.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public RestauranteDTO atualizar(@PathVariable Long restauranteId,
                                    @RequestBody @Valid RestauranteInputDTO restauranteInputDTO) {
        try {

            Restaurante restauranteAtual = restauranteService.buscarOuFalhar(restauranteId);

            disassembler.toCopyDomainObject(restauranteInputDTO, restauranteAtual);

            return assembler.getRestauranteDTO(restauranteService.salvar(restauranteAtual));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long restauranteId) {
        restauranteService.ativar(restauranteId);
    }

    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long restauranteId) {
        restauranteService.inativar(restauranteId);
    }

    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> restaurantesId) {
        restauranteService.ativarEmMassa(restaurantesId);
    }

    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarMultiplos(@RequestBody List<Long> restaurantesId) {
        restauranteService.desativarEmMassa(restaurantesId);
    }

    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abertura(@PathVariable Long restauranteId) {
        restauranteService.abrir(restauranteId);
    }


    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechamento(@PathVariable Long restauranteId) {
        restauranteService.fechar(restauranteId);
    }

}
