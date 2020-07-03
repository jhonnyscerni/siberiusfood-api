package br.com.siberius.siberiusfood.listener;

import br.com.siberius.siberiusfood.event.PedidoCanceladoEvent;
import br.com.siberius.siberiusfood.model.Pedido;
import br.com.siberius.siberiusfood.service.EnvioEmailService;
import br.com.siberius.siberiusfood.service.EnvioEmailService.Mensagem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoClientePedidoCanceladoListener {

    @Autowired
    private EnvioEmailService envioEmail;

    @TransactionalEventListener
    public void aoCancelarPedido(PedidoCanceladoEvent event) {
        Pedido pedido = event.getPedido();

        Mensagem mensagem = Mensagem.builder()
            .assunto(pedido.getRestaurante().getNome() + " - Pedido cancelado")
            .corpo("pedido-cancelado.html")
            .variavel("pedido", pedido)
            .destinatario(pedido.getCliente().getEmail())
            .build();

        envioEmail.enviar(mensagem);
    }
}
