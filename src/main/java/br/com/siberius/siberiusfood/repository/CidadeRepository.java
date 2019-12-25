package br.com.siberius.siberiusfood.repository;

import br.com.siberius.siberiusfood.model.Cidade;

import java.util.List;

public interface CidadeRepository {

    List<Cidade> listar();

    Cidade buscar(Long id);

    Cidade salvar(Cidade cidade);

    void remover(Cidade cidade);

}
