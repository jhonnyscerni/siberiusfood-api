package br.com.siberius.siberiusfood.repository;

import br.com.siberius.siberiusfood.model.Pedido;
import br.com.siberius.siberiusfood.repository.custom.CustomJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {
}
