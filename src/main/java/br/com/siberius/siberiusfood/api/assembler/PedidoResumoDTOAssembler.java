package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.SiberiusLinks;
import br.com.siberius.siberiusfood.api.controller.PedidoController;
import br.com.siberius.siberiusfood.api.model.PedidoResumoDTO;
import br.com.siberius.siberiusfood.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoResumoDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SiberiusLinks siberiusLinks;

    public PedidoResumoDTOAssembler() {
        super(PedidoController.class, PedidoResumoDTO.class);
    }

    @Override
    public PedidoResumoDTO toModel(Pedido pedido) {
        PedidoResumoDTO pedidoDTO = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoDTO);

        pedidoDTO.add(siberiusLinks.linkToPedidos("pedidos"));

        pedidoDTO.getRestaurante().add(
            siberiusLinks.linkToRestaurante(pedido.getRestaurante().getId()));

        pedidoDTO.getCliente().add(siberiusLinks.linkToUsuario(pedido.getCliente().getId()));

        return pedidoDTO;
    }

//    public PedidoResumoDTO getPedidoResumoDTO(Pedido pedido) {
//        return modelMapper.map(pedido, PedidoResumoDTO.class);
//    }
//
//    public List<PedidoResumoDTO> getListPedidoResumoDTO(List<Pedido> pedidos) {
//        return pedidos.stream()
//                .map(pedido -> getPedidoResumoDTO(pedido))
//                .collect(Collectors.toList());
//    }
}
