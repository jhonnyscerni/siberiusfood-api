package br.com.siberius.siberiusfood.repository;

import br.com.siberius.siberiusfood.model.Restaurante;

import java.util.List;

public interface RestauranteRepository {

    List<Restaurante> listar();

    Restaurante buscar(Long id);

    Restaurante salvar(Restaurante restaurante);

    void remover(Restaurante restaurante);

}
