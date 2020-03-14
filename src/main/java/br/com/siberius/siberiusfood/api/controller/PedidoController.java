package br.com.siberius.siberiusfood.api.controller;

import br.com.siberius.siberiusfood.api.assembler.PedidoDTOAssembler;
import br.com.siberius.siberiusfood.api.assembler.PedidoDTODisassembler;
import br.com.siberius.siberiusfood.api.assembler.PedidoResumoDTOAssembler;
import br.com.siberius.siberiusfood.api.model.PedidoDTO;
import br.com.siberius.siberiusfood.api.model.PedidoResumoDTO;
import br.com.siberius.siberiusfood.api.model.input.PedidoInputDTO;
import br.com.siberius.siberiusfood.exception.EntidadeNaoEncontradaException;
import br.com.siberius.siberiusfood.exception.NegocioException;
import br.com.siberius.siberiusfood.model.Pedido;
import br.com.siberius.siberiusfood.model.Usuario;
import br.com.siberius.siberiusfood.repository.PedidoRepository;
import br.com.siberius.siberiusfood.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @Autowired
    private PedidoDTODisassembler disassembler;

    @GetMapping
    public List<PedidoResumoDTO> listar() {
        return assemblerResumo.getListPedidoResumoDTO(pedidoRepository.findAll());
    }

    @GetMapping("/{pedidoId}")
    public PedidoDTO buscar(@PathVariable Long pedidoId) {
        Pedido pedido = pedidoService.buscarOuFalhar(pedidoId);

        return assembler.getPedidoDTO(pedido);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO salvar(@RequestBody @Valid PedidoInputDTO pedidoInputDTO) {
        try {

            Pedido novoPedido = disassembler.getPedidoObject(pedidoInputDTO);

            // TODO pegar usuário autenticado
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);

            return assembler.getPedidoDTO(pedidoService.emitir(novoPedido));

        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }
}
