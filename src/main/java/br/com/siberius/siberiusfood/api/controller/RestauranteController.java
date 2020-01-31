package br.com.siberius.siberiusfood.api.controller;

import br.com.siberius.siberiusfood.exception.EntidadeNaoEncontradaException;
import br.com.siberius.siberiusfood.model.Restaurante;
import br.com.siberius.siberiusfood.repository.RestauranteRepository;
import br.com.siberius.siberiusfood.service.RestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private RestauranteService restauranteService;

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(restauranteRepository.findAll());
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<?> buscar(@PathVariable Long restauranteId) {
        Optional<Restaurante> restaurante = restauranteRepository.findById(restauranteId);

        if (restaurante.isPresent()) {
            return ResponseEntity.ok(restaurante.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Restaurante restaurante) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(restauranteService.salvar(restaurante));
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {
        try {
            Restaurante restauranteAtual = restauranteRepository.findById(restauranteId).orElse(null);

            if (restauranteAtual != null) {
                BeanUtils.copyProperties(restaurante, restauranteAtual,
                        "id", "formaPagamentos", "endereco", "dataCadastro", "produtos");
                restauranteAtual = restauranteService.salvar(restauranteAtual);
                return ResponseEntity.ok(restauranteAtual);
            }

            return ResponseEntity.notFound().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{restauranteId}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId,
                                              @RequestBody Map<String, Object> campos) {

        Restaurante restauranteAtual = restauranteRepository.findById(restauranteId).orElse(null);

        if (restauranteAtual == null) {
            ResponseEntity.notFound().build();
        }

        merge(campos, restauranteAtual);

        return atualizar(restauranteId, restauranteAtual);
    }

    private void merge(@RequestBody Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
        ObjectMapper objectMapper = new ObjectMapper();
        /**
         * Cria para mim uma instancia de Restaurante.class usando como base esse mapa de dados Origem para nao
         * da mais erro de conversao
         */
        Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

        dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
            /**
             *   if (nomePropriedade.equals("nome")) {
             *                 restauranteDestino.setNome((String) valorPropriedade);
             *   }
             */

            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            // Como campo e privado vamos da Acesso a ele
            field.setAccessible(true);

            // novoValor ja Convertido
            Object novovalor = ReflectionUtils.getField(field, restauranteOrigem);

            // System.out.println(nomePropriedade + " = " + valorPropriedade + " = " + novovalor);

            ReflectionUtils.setField(field, restauranteDestino, novovalor);
        });
    }
}
