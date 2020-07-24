package br.com.siberius.siberiusfood.model;

import java.util.Arrays;
import java.util.List;

public enum StatusPedido {

    CRIADO("Criado"),
    CONFIRMADO("Confirmado", CRIADO),
    ENTREGUE("Entregue", CONFIRMADO),
    CANCELADO("Cancelado", CRIADO, CONFIRMADO);

    private String descricao;
    private List<StatusPedido> statusPedidos;

    StatusPedido(String descricao, StatusPedido... statusAnteriores) {
        this.descricao = descricao;
        this.statusPedidos = Arrays.asList(statusAnteriores);
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean naoPodeAlterarPara(StatusPedido statusNovo) {
        return !statusNovo.statusPedidos.contains(this);
    }

    public boolean podePodeAlterarPara(StatusPedido statusNovo) {
        return !naoPodeAlterarPara(statusNovo);
    }
}
