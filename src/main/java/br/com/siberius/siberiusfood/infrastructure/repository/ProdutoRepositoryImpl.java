package br.com.siberius.siberiusfood.infrastructure.repository;

import br.com.siberius.siberiusfood.model.FotoProduto;
import br.com.siberius.siberiusfood.repository.ProdutoRepositoryQueries;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    @Override
    public FotoProduto save(FotoProduto foto) {
        return manager.merge(foto);
    }

    @Override
    public void delete(FotoProduto foto) {
        manager.remove(foto);
    }

}