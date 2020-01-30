package br.com.siberius.siberiusfood.infrastructure.repository;

import br.com.siberius.siberiusfood.model.Restaurante;
import br.com.siberius.siberiusfood.repository.RestauranteRepository;
import br.com.siberius.siberiusfood.repository.RestauranteRepositoryQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static br.com.siberius.siberiusfood.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static br.com.siberius.siberiusfood.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;


@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    @Lazy
    private RestauranteRepository restauranteRepository;

    @Override
    public List<Restaurante> find(String nome,
                                  BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {

        StringBuilder jpql = new StringBuilder();
        //var jpql = new StringBuilder();
        jpql.append("from Restaurante where 0 = 0 ");

        Map<String, Object> parametros = new HashMap<>();
        // var parametros = new HashMap<String, Object>();

        if (StringUtils.hasLength(nome)) {
            jpql.append("and nome like :nome ");
            parametros.put("nome", "%" + nome + "%");
        }

        if (taxaFreteInicial != null) {
            jpql.append("and taxaFrete >= :taxaInicial ");
            parametros.put("taxaInicial", taxaFreteInicial);
        }

        if (taxaFreteFinal != null) {
            jpql.append("and taxaFrete <= :taxaFinal ");
            parametros.put("taxaFinal", taxaFreteFinal);
        }

        TypedQuery<Restaurante> query = manager
                .createQuery(jpql.toString(), Restaurante.class);

        parametros.forEach((chave, valor) -> query.setParameter(chave, valor));

        return query.getResultList();

    }

    @Override
    public List<Restaurante> findCriteria(String nome,
                                          BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Restaurante> criteriaQuery = builder.createQuery(Restaurante.class);
        Root<Restaurante> root = criteriaQuery.from(Restaurante.class);

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(nome)) {
            predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
        }

        if (taxaFreteInicial != null) {
            predicates.add(
                    builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
        }

        if (taxaFreteFinal != null) {
            predicates.add(
                    builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
        }

        //criteriaQuery.where(nomePredicate, taxaFreteInicialPredicate, taxaFreteFinalPredicate);
        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Restaurante> restauranteTypedQuery = manager.createQuery(criteriaQuery);

        return restauranteTypedQuery.getResultList();

    }

    @Override
    public List<Restaurante> findComFreteGratis(String nome) {
        return restauranteRepository.findAll(comFreteGratis()
                .and(comNomeSemelhante(nome)));
    }

}