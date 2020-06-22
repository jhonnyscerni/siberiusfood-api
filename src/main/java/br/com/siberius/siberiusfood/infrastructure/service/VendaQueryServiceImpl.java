package br.com.siberius.siberiusfood.infrastructure.service;

import br.com.siberius.siberiusfood.filter.VendaDiariaFilter;
import br.com.siberius.siberiusfood.model.Pedido;
import br.com.siberius.siberiusfood.model.dto.VendaDiaria;
import br.com.siberius.siberiusfood.service.VendaQueryService;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter) {
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

        Expression<Date> functionDateDataCriacao = builder.function("date", Date.class, root.get("dataCriacao"));

        CompoundSelection<VendaDiaria> selection = builder.construct(VendaDiaria.class,
            functionDateDataCriacao,
            builder.count(root.get("id")),
            builder.sum(root.get("valorTotal")));

        query.select(selection);
        query.groupBy(functionDateDataCriacao);

        return manager.createQuery(query).getResultList();
    }
}
