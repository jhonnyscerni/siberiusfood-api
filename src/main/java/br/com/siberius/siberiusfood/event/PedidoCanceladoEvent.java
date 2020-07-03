package br.com.siberius.siberiusfood.event;

import br.com.siberius.siberiusfood.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PedidoCanceladoEvent {

    private Pedido pedido;

}