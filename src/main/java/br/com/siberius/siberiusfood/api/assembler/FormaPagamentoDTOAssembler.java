package br.com.siberius.siberiusfood.api.assembler;

import br.com.siberius.siberiusfood.api.SiberiusLinks;
import br.com.siberius.siberiusfood.api.controller.FormaPagamentoController;
import br.com.siberius.siberiusfood.api.model.FormaPagamentoDTO;
import br.com.siberius.siberiusfood.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoDTOAssembler
    extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SiberiusLinks siberiusLinks;

    public FormaPagamentoDTOAssembler() {
        super(FormaPagamentoController.class, FormaPagamentoDTO.class);
    }

    @Override
    public FormaPagamentoDTO toModel(FormaPagamento formaPagamento) {
        FormaPagamentoDTO formaPagamentoModel =
            createModelWithId(formaPagamento.getId(), formaPagamento);

        modelMapper.map(formaPagamento, formaPagamentoModel);

        formaPagamentoModel.add(siberiusLinks.linkToFormasPagamento("formasPagamento"));

        return formaPagamentoModel;
    }

    @Override
    public CollectionModel<FormaPagamentoDTO> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
        return super.toCollectionModel(entities)
            .add(siberiusLinks.linkToFormasPagamento());
    }

//    public FormaPagamentoDTO getFormaPagamentoDTO(FormaPagamento formaPagamento) {
//        return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
//    }
//
//    public List<FormaPagamentoDTO> getListFormaPagamentoDTO(Collection<FormaPagamento> formasPagamento) {
//        return formasPagamento.stream()
//                .map(formaPagamento -> getFormaPagamentoDTO(formaPagamento))
//                .collect(Collectors.toList());
//    }

}
