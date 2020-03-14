package br.com.siberius.siberiusfood.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public GrupoNaoEncontradoException(String message) {
        super(message);
    }

    public GrupoNaoEncontradoException(Long grupoId){
        this(String.format("Não existe cadastro de Grupo com código %d", grupoId));
    }
}
