package br.com.siberius.siberiusfood.repository;

import br.com.siberius.siberiusfood.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;

public interface RestauranteRepositoryQueries {

    List<Restaurante> find(String nome,
                           BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

    List<Restaurante> findCriteria(String nome,
                                   BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

    List<Restaurante> findComFreteGratis(String nome);
}
