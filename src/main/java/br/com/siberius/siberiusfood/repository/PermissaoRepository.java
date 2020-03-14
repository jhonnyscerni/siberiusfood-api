package br.com.siberius.siberiusfood.repository;

import br.com.siberius.siberiusfood.model.Grupo;
import br.com.siberius.siberiusfood.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
}
