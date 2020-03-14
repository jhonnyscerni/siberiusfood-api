package br.com.siberius.siberiusfood.repository;

import br.com.siberius.siberiusfood.model.Usuario;
import br.com.siberius.siberiusfood.repository.custom.CustomJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    void detach(Usuario usuario);
}
