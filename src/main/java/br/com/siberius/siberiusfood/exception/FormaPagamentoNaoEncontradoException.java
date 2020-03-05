package br.com.siberius.siberiusfood.exception;

public class FormaPagamentoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public FormaPagamentoNaoEncontradoException(String message) {
        super(message);
    }

    public FormaPagamentoNaoEncontradoException(Long formaPagamentoId) {
        this(String.format("Não existe cadastro de Forma de Pagamento com código %d", formaPagamentoId));
    }
}
