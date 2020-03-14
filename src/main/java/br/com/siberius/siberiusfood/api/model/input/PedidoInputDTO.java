package br.com.siberius.siberiusfood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class PedidoInputDTO {

    @Valid
    @NotNull
    private RestauranteIdInputDTO restaurante;

    @Valid
    @NotNull
    private EnderecoInputDTO endereco;

    @Valid
    @NotNull
    private FormaPagamentoIdInputDTO formaPagamento;

    @Valid
    @NotNull
    @Size(min = 1)
    private List<ItemPedidoInputDTO> itens;

}
