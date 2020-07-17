package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.controller.PedidoController;
import br.com.siberius.siberiusfood.api.controller.RestauranteController;
import br.com.siberius.siberiusfood.api.controller.UsuarioController;
import br.com.siberius.siberiusfood.api.model.PedidoResumoDTO;
import br.com.siberius.siberiusfood.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoResumoDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoResumoDTOAssembler() {
        super(PedidoController.class, PedidoResumoDTO.class);
    }

    @Override
    public PedidoResumoDTO toModel(Pedido pedido) {
        PedidoResumoDTO pedidoDTO = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoDTO);

        pedidoDTO.add(WebMvcLinkBuilder.linkTo(PedidoController.class).withRel("pedidos"));

        pedidoDTO.getRestaurante().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class)
            .buscar(pedido.getRestaurante().getId())).withSelfRel());

        pedidoDTO.getCliente().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class)
            .buscar(pedido.getCliente().getId())).withSelfRel());

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
