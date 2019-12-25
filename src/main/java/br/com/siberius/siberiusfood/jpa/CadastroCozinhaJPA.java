package br.com.siberius.siberiusfood.jpa;

import br.com.siberius.siberiusfood.model.Cozinha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class CadastroCozinhaJPA {

    @Autowired
    private EntityManager entityManager;

    public List<Cozinha> findAll() {
        return entityManager.createQuery("from Cozinha", Cozinha.class).getResultList();
    }

    public Cozinha search(Long id) {
        return entityManager.find(Cozinha.class, id);
    }

    @Transactional
    public Cozinha save(Cozinha cozinha) {
        return entityManager.merge(cozinha);
    }

    @Transactional
    public void remove(Cozinha cozinha) {
        cozinha = search(cozinha.getId());
        entityManager.remove(cozinha);
    }

}
