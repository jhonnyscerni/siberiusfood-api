package br.com.siberius.siberiusfood.api.controller;

import br.com.siberius.siberiusfood.api.assembler.PedidoDTOAssembler;
import br.com.siberius.siberiusfood.api.assembler.PedidoResumoDTOAssembler;
import br.com.siberius.siberiusfood.api.model.PedidoDTO;
import br.com.siberius.siberiusfood.api.model.PedidoResumoDTO;
import br.com.siberius.siberiusfood.model.Pedido;
import br.com.siberius.siberiusfood.repository.PedidoRepository;
import br.com.siberius.siberiusfood.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/pedidos")
@RestController
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoDTOAssembler assembler;

    @Autowired
    private PedidoResumoDTOAssembler assemblerResumo;

    @GetMapping
    public List<PedidoResumoDTO> listar() {
        return assemblerResumo.getListPedidoResumoDTO(pedidoRepository.findAll());
    }

    @GetMapping("/{pedidoId}")
    public PedidoDTO buscar(@PathVariable Long pedidoId) {
        Pedido pedido = pedidoService.buscarOuFalhar(pedidoId);

        return assembler.getPedidoDTO(pedido);
    }
}
