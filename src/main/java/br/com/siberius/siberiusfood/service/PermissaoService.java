package br.com.siberius.siberiusfood.service;

import br.com.siberius.siberiusfood.exception.PermissaoNaoEncontradaExcepetion;
import br.com.siberius.siberiusfood.model.Permissao;
import br.com.siberius.siberiusfood.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Transactional
    public Permissao salvar(Permissao permissao){
        return permissaoRepository.save(permissao);
    }

    public Permissao buscarOuFalhar(Long permissaoId) {
        return permissaoRepository.findById(permissaoId)
                .orElseThrow(() -> new PermissaoNaoEncontradaExcepetion(permissaoId));
    }
}
