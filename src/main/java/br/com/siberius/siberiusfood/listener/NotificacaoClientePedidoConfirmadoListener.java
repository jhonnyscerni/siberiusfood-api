package br.com.siberius.siberiusfood.listener;

import br.com.siberius.siberiusfood.event.PedidoConfirmadoEvent;
import br.com.siberius.siberiusfood.model.Pedido;
import br.com.siberius.siberiusfood.service.EnvioEmailService;
import br.com.siberius.siberiusfood.service.EnvioEmailService.Mensagem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoClientePedidoConfirmadoListener {

    @Autowired
    private EnvioEmailService envioEmailService;

    @EventListener
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
