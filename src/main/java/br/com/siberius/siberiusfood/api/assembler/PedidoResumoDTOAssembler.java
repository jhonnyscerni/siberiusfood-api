package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.model.PedidoResumoDTO;
import br.com.siberius.siberiusfood.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoResumoDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoResumoDTO getPedidoResumoDTO(Pedido pedido) {
        return modelMapper.map(pedido, PedidoResumoDTO.class);
    }

    public List<PedidoResumoDTO> getListPedidoResumoDTO(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(pedido -> getPedidoResumoDTO(pedido))
                .collect(Collectors.toList());
    }
}
