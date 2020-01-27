package br.com.siberius.siberiusfood.service;

import br.com.siberius.siberiusfood.exception.EntidadeNaoEncontradaException;
import br.com.siberius.siberiusfood.model.Cozinha;
import br.com.siberius.siberiusfood.model.Restaurante;
import br.com.siberius.siberiusfood.repository.CozinhaRepository;
import br.com.siberius.siberiusfood.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Restaurante salvar(Restaurante restaurante) {

        Cozinha cozinha = cozinhaRepository.findById(restaurante.getCozinha().getId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Não existe cadastro de cozinha com o codigo %d", restaurante.getCozinha().getId())));

        restaurante.setCozinha(cozinha);
        return restauranteRepository.save(restaurante);
    }
}
