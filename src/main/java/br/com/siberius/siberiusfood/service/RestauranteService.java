package br.com.siberius.siberiusfood.service;

import br.com.siberius.siberiusfood.exception.RestauranteNaoEncontradoException;
import br.com.siberius.siberiusfood.model.Cozinha;
import br.com.siberius.siberiusfood.model.Restaurante;
import br.com.siberius.siberiusfood.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaService cozinhaService;

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {

        Cozinha cozinha = cozinhaService.buscarOuFalhar(restaurante.getCozinha().getId());
        restaurante.setCozinha(cozinha);
        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public void ativar(Long restauranteId){
        Restaurante restaurante = buscarOuFalhar(restauranteId);

        restaurante.ativar();
    }

    @Transactional
    public void inativar(Long restauranteId){
        Restaurante restaurante = buscarOuFalhar(restauranteId);

        restaurante.inativar();
    }

    public Restaurante buscarOuFalhar(Long restauranteId) {
        return restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
    }
}
