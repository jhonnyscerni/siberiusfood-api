package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.model.FormaPagamentoDTO;
import br.com.siberius.siberiusfood.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormaPagamentoDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamentoDTO getFormaPagamentoDTO(FormaPagamento formaPagamento){
        return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
    }

    public List<FormaPagamentoDTO> getListFormaPagamentoDTO(List<FormaPagamento> formasPagamento){
        return formasPagamento.stream()
                .map(formaPagamento -> getFormaPagamentoDTO(formaPagamento))
                .collect(Collectors.toList());
    }

}
