package br.com.siberius.siberiusfood.api.controller;

import br.com.siberius.siberiusfood.api.assembler.RestauranteDTOAssembler;
import br.com.siberius.siberiusfood.api.assembler.RestauranteInputDisassembler;
import br.com.siberius.siberiusfood.api.model.RestauranteDTO;
import br.com.siberius.siberiusfood.api.model.input.RestauranteInputDTO;
import br.com.siberius.siberiusfood.exception.CozinhaNaoEncontradaException;
import br.com.siberius.siberiusfood.exception.NegocioException;
import br.com.siberius.siberiusfood.model.Cozinha;
import br.com.siberius.siberiusfood.model.Restaurante;
import br.com.siberius.siberiusfood.repository.RestauranteRepository;
import br.com.siberius.siberiusfood.service.RestauranteService;
import org.springframework.beans.BeanUtils;
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
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public RestauranteDTO atualizar(@PathVariable Long restauranteId,
                                    @RequestBody @Valid RestauranteInputDTO restauranteInputDTO) {
        try {
            Restaurante restaurante = disassembler.getRestauranteObject(restauranteInputDTO);

            Restaurante restauranteAtual = restauranteService.buscarOuFalhar(restauranteId);

            BeanUtils.copyProperties(restaurante, restauranteAtual,
                    "id", "formaPagamentos", "endereco", "dataCadastro", "produtos");

            return assembler.getRestauranteDTO(restauranteService.salvar(restauranteAtual));
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }
}
