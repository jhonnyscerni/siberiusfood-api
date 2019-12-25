package br.com.siberius.siberiusfood.repository;

import br.com.siberius.siberiusfood.model.Estado;

import java.util.List;

public interface EstadoRepository {

    List<Estado> listar();

    Estado buscar(Long id);

    Estado salvar(Estado estado);

    void remover(Estado estado);

}
