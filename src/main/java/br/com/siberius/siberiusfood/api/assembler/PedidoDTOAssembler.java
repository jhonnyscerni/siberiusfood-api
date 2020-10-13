package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.SiberiusLinks;
import br.com.siberius.siberiusfood.api.controller.PedidoController;
import br.com.siberius.siberiusfood.api.model.PedidoDTO;
import br.com.siberius.siberiusfood.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoDTOAssembler() {
        super(PedidoController.class, PedidoDTO.class);
    }

    @Autowired
    private SiberiusLinks siberiusLinks;

    @Override
    public PedidoDTO toModel(Pedido pedido) {
        PedidoDTO pedidoDTO = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoDTO);

        pedidoDTO.add(siberiusLinks.linkToPedidos("pedidos"));

        if (pedido.podeSerConfirmado()) {
            pedidoDTO.add(siberiusLinks.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
        }

        if (pedido.podeSerCancelado()) {
            pedidoDTO.add(siberiusLinks.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
        }

        if (pedido.podeSerEntregue()) {
            pedidoDTO.add(siberiusLinks.linkToEntregaPedido(pedido.getCodigo(), "entregar"));
        }

        pedidoDTO.getRestaurante().add(
            siberiusLinks.linkToRestaurante(pedido.getRestaurante().getId()));

        pedidoDTO.getCliente().add(
            siberiusLinks.linkToUsuario(pedido.getCliente().getId()));

        pedidoDTO.getFormaPagamento().add(
            siberiusLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));

        pedidoDTO.getEnderecoEntrega().getCidade().add(
            siberiusLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));

        pedidoDTO.getItens().forEach(item -> {
            item.add(siberiusLinks.linkToProduto(
                pedidoDTO.getRestaurante().getId(), item.getProdutoId(), "produto"));
        });

        return pedidoDTO;
    }

}
