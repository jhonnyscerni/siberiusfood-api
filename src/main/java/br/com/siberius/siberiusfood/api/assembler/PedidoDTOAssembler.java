package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.model.PedidoDTO;
import br.com.siberius.siberiusfood.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoDTO getPedidoDTO(Pedido pedido) {
        return modelMapper.map(pedido, PedidoDTO.class);
    }

    public List<PedidoDTO> getListPedidoDTO(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(pedido -> getPedidoDTO(pedido))
                .collect(Collectors.toList());
    }
}
