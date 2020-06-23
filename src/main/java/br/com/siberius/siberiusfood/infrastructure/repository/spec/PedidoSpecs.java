package br.com.siberius.siberiusfood.infrastructure.repository.spec;

import br.com.siberius.siberiusfood.model.Pedido;
import br.com.siberius.siberiusfood.filter.PedidoFilter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;;
import org.springframework.data.jpa.domain.Specification;

public class PedidoSpecs {

    public static Specification<Pedido> usandoFiltro(PedidoFilter filtro) {
        return (root, query, builder) -> {

            if (Pedido.class.equals(query.getResultType())) {
                //resolvendo problema do N+1 (fetch)
                root.fetch("restaurante").fetch("cozinha");
                root.fetch("cliente");
            }

            List<Predicate> predicates = new ArrayList<Predicate>();

            if (filtro.getClienteId() != null) {
                predicates.add(builder.equal(root.get("cliente"), filtro.getClienteId()));
            }

            if (filtro.getRestauranteId() != null) {
                predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));
            }

            if (filtro.getDataCriacaoInicio() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"),
                    filtro.getDataCriacaoInicio()));
            }

            if (filtro.getDataCriacaoFim() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"),
                    filtro.getDataCriacaoFim()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

}