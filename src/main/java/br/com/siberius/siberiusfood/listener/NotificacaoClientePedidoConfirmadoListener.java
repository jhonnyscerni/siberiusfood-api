package br.com.siberius.siberiusfood.listener;

import br.com.siberius.siberiusfood.event.PedidoConfirmadoEvent;
import br.com.siberius.siberiusfood.model.Pedido;
import br.com.siberius.siberiusfood.service.EnvioEmailService;
import br.com.siberius.siberiusfood.service.EnvioEmailService.Mensagem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoClientePedidoConfirmadoListener {

    @Autowired
    private EnvioEmailService envioEmailService;

    // Caso de erro no envio do email ele faz rollback de tudo
    // @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    @TransactionalEventListener
    public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
        Pedido pedido = event.getPedido();

        Mensagem mensagem = Mensagem.builder()
            .assunto(pedido.getRestaurante().getNome() + " - Pedido Confirmado")
            .corpo("pedido-confirmado.html")
            .variavel("pedido", pedido)
            .destinatario(pedido.getCliente().getEmail())
            .build();
        envioEmailService.enviar(mensagem);
    }
}
