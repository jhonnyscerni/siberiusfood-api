package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.model.input.FormaPagamentoInputDTO;
import br.com.siberius.siberiusfood.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamento getFormaPagamentoObject(FormaPagamentoInputDTO formaPagamentoInputDTO){
        return modelMapper.map(formaPagamentoInputDTO, FormaPagamento.class);
    }

    public void copyToDomainObject(FormaPagamentoInputDTO formaPagamentoInputDTO, FormaPagamento formaPagamento)
    {
        modelMapper.map(formaPagamentoInputDTO, formaPagamento);
    }
}
