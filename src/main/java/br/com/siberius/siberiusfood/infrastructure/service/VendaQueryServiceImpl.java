package br.com.siberius.siberiusfood.infrastructure.service;

import br.com.siberius.siberiusfood.filter.VendaDiariaFilter;
import br.com.siberius.siberiusfood.model.Pedido;
import br.com.siberius.siberiusfood.model.dto.VendaDiaria;
import br.com.siberius.siberiusfood.service.VendaQueryService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter, String timeOffset) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        /**
         *  select * from date(p.data_criacao) as data_criacao,
         *  count(p.id) as total_vendas,
         *  sum(p.valor_total) as total_faturado
         *
         *  from pedido p
         *
         *  group by date(p.data_criacao)
         */

        CriteriaQuery<VendaDiaria> query = builder.createQuery(VendaDiaria.class);
        Root<Pedido> root = query.from(Pedido.class);
        List<Predicate> predicates = new ArrayList<Predicate>();

        Expression<Date> functionConvertTzDataCriacao = builder.function(
            "convert_tz", Date.class, root.get("dataCriacao"),
            builder.literal("+00:00"), builder.literal(timeOffset));

        Expression<Date> functionDateDataCriacao = builder.function("date", Date.class, functionConvertTzDataCriacao);

        CompoundSelection<VendaDiaria> selection = builder.construct(VendaDiaria.class,
            functionDateDataCriacao,
            builder.count(root.get("id")),
            builder.sum(root.get("valorTotal")));

        if (filter.getRestauranteId() != null) {
            predicates.add(builder.equal(root.get("restaurante"), filter.getRestauranteId()));
        }

        if (filter.getDataCriacaoInicio() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"),
                filter.getDataCriacaoInicio()));
        }

        if (filter.getDataCriacaoFim() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"),
                filter.getDataCriacaoFim()));
        }

//        predicates.add(root.get("status").in(
//            StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(functionDateDataCriacao);

        return manager.createQuery(query).getResultList();
    }
}
