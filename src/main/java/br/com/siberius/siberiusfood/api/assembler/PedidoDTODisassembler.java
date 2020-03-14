package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.model.input.PedidoInputDTO;
import br.com.siberius.siberiusfood.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Pedido getPedidoObject(PedidoInputDTO pedidoInputDTO){
        return modelMapper.map(pedidoInputDTO, Pedido.class);
    }

    public void copyToDomainObject(PedidoInputDTO pedidoInputDTO, Pedido pedido) {
        modelMapper.map(pedidoInputDTO, pedido);
    }
}
