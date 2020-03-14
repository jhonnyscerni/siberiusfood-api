package br.com.siberius.siberiusfood.repository;

import br.com.siberius.siberiusfood.model.Pedido;
import br.com.siberius.siberiusfood.repository.custom.CustomJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {

    @Query("from Pedido p join fetch p.cliente join fetch p.restaurante r join fetch r.cozinha")
    List<Pedido> findAll();
}
