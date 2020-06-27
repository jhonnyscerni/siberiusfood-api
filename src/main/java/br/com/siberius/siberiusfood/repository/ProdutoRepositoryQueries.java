package br.com.siberius.siberiusfood.repository;

import br.com.siberius.siberiusfood.model.FotoProduto;

public interface ProdutoRepositoryQueries {

    FotoProduto save(FotoProduto foto);

    void delete(FotoProduto foto);
}
