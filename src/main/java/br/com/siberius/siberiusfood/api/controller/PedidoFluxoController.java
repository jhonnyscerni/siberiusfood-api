package br.com.siberius.siberiusfood.api.controller;

import br.com.siberius.siberiusfood.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/{codigoPedido}")
public class PedidoFluxoController {

    @Autowired
    private PedidoService pedidoService;

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void confirmar(@PathVariable String codigoPedido) {
        pedidoService.confirmar(codigoPedido);
    }

    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void cancelar(@PathVariable String codigoPedido) {
        pedidoService.cancelar(codigoPedido);
    }

    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void entregar(@PathVariable String codigoPedido) {
        pedidoService.entregar(codigoPedido);
    }
}
