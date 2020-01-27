package br.com.siberius.siberiusfood.repository;

import br.com.siberius.siberiusfood.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

}
