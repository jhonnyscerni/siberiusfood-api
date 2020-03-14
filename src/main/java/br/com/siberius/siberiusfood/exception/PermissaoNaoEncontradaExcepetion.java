package br.com.siberius.siberiusfood.exception;

public class PermissaoNaoEncontradaExcepetion extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public PermissaoNaoEncontradaExcepetion(String message) {
        super(message);
    }

    public PermissaoNaoEncontradaExcepetion(Long permissaoId) {
        this(String.format("Não existe um cadastro de permissao com o código %s", permissaoId));
    }
}
