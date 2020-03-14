package br.com.siberius.siberiusfood.api.controller;

import br.com.siberius.siberiusfood.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/{pedidoId}")
public class PedidoFluxoController {

    @Autowired
    private PedidoService pedidoService;

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void confirmar(@PathVariable Long pedidoId) {
        pedidoService.confirmar(pedidoId);
    }
}
