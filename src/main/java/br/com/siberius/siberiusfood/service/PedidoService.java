package br.com.siberius.siberiusfood.service;

import br.com.siberius.siberiusfood.exception.PedidoNaoEncontradoException;
import br.com.siberius.siberiusfood.model.Pedido;
import br.com.siberius.siberiusfood.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido buscarOuFalhar(Long pedidoId) {
        return pedidoRepository.findById(pedidoId).orElseThrow(
                () -> new PedidoNaoEncontradoException(pedidoId)
        );
    }
}
