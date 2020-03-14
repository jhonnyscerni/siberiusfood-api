package br.com.siberius.siberiusfood.service;

import br.com.siberius.siberiusfood.exception.EntidadeEmUsoException;
import br.com.siberius.siberiusfood.exception.NegocioException;
import br.com.siberius.siberiusfood.exception.UsuarioNaoEncontradoException;
import br.com.siberius.siberiusfood.model.Grupo;
import br.com.siberius.siberiusfood.model.Usuario;
import br.com.siberius.siberiusfood.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GrupoService grupoService;

    @Transactional
    public Usuario salvar(Usuario usuario) {

        // Problema com @Transactional pois ele efetua o Commit sempre que a entidade e atualizada.
        // Entao estou tirando a transactional para ele nao efetuar nenhum UPDATE e duplicar os emails
        usuarioRepository.detach(usuario);

        Optional<Usuario> usuarioEmailExistente =
                usuarioRepository.findByEmail(usuario.getEmail());

        if (usuarioEmailExistente.isPresent() && !usuarioEmailExistente.get().equals(usuario)){
            throw new NegocioException(
                    String.format("Já existe uauario cadastrado com e-mail %s", usuario.getEmail())
            );
        }
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void associarGrupo(Long usuarioId, Long grupoId){
        Usuario usuario = buscarOuFalhar(usuarioId);
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);

        usuario.adicionarGrupo(grupo);
    }

    @Transactional
    public void desassociarGrupo(Long usuarioId, Long grupoId){
        Usuario usuario = buscarOuFalhar(usuarioId);
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);

        usuario.removerGrupo(grupo);
    }

    @Transactional
    public void excluir(Long usuarioId){
        try {
            usuarioRepository.deleteById(usuarioId);
            usuarioRepository.flush();
        }catch (EmptyResultDataAccessException e){
            throw new UsuarioNaoEncontradoException(usuarioId);
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(
                    String.format("Usuario de códido %s não pode ser removido, pois está em uso"));
        }
    }

    @Transactional
    public Usuario buscarOuFalhar(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
    }

    @Transactional
    public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
        Usuario usuario = buscarOuFalhar(usuarioId);

        if (usuario.senhaNaoCoincideCom(senhaAtual)){
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }

        usuario.setSenha(novaSenha);
    }
}
