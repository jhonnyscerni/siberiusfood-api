package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.controller.CidadeController;
import br.com.siberius.siberiusfood.api.controller.FormaPagamentoController;
import br.com.siberius.siberiusfood.api.controller.PedidoController;
import br.com.siberius.siberiusfood.api.controller.RestauranteController;
import br.com.siberius.siberiusfood.api.controller.RestauranteProdutoController;
import br.com.siberius.siberiusfood.api.controller.UsuarioController;
import br.com.siberius.siberiusfood.api.model.PedidoDTO;
import br.com.siberius.siberiusfood.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import org.springframework.hateoas.Link;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoDTOAssembler() {
        super(PedidoController.class, PedidoDTO.class);
    }

    @Override
    public PedidoDTO toModel(Pedido pedido) {
        PedidoDTO pedidoDTO = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoDTO);

        TemplateVariables pageVariables = new TemplateVariables(
            new TemplateVariable("page", VariableType.REQUEST_PARAM),
            new TemplateVariable("size", VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", VariableType.REQUEST_PARAM));

        String pedidosUrl = WebMvcLinkBuilder.linkTo(PedidoController.class).toUri().toString();

        pedidoDTO.add(new Link(UriTemplate.of(pedidosUrl, pageVariables), "pedidos"));

//        pedidoDTO.add(WebMvcLinkBuilder.linkTo(PedidoController.class).withRel("pedidos"));

        pedidoDTO.getRestaurante().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class)
            .buscar(pedido.getRestaurante().getId())).withSelfRel());

        pedidoDTO.getCliente().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class)
            .buscar(pedido.getCliente().getId())).withSelfRel());

        // Passamos null no segundo argumento, porque é indiferente para a
        // construção da URL do recurso de forma de pagamento
        pedidoDTO.getFormaPagamento().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FormaPagamentoController.class)
            .buscar(pedido.getFormaPagamento().getId(), null)).withSelfRel());

        pedidoDTO.getEnderecoEntrega().getCidade().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class)
            .buscar(pedido.getEnderecoEntrega().getCidade().getId())).withSelfRel());

        pedidoDTO.getItens().forEach(item -> {
            item.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteProdutoController.class)
                .buscar(pedidoDTO.getRestaurante().getId(), item.getProdutoId()))
                .withRel("produto"));
        });

        return pedidoDTO;
    }

//    public PedidoDTOAssembler() {
//        super(PedidoController.class, PedidoDTO.class);
//    }
//
//    public PedidoDTO getPedidoDTO(Pedido pedido) {
//        return modelMapper.map(pedido, PedidoDTO.class);
//    }
//
//    public List<PedidoDTO> getListPedidoDTO(List<Pedido> pedidos) {
//        return pedidos.stream()
//                .map(pedido -> getPedidoDTO(pedido))
//                .collect(Collectors.toList());
//    }
}
