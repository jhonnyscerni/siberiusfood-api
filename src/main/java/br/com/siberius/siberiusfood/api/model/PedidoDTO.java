package br.com.siberius.siberiusfood.api.model;

import br.com.siberius.siberiusfood.model.ItemPedidoDTO;
import br.com.siberius.siberiusfood.model.RestauranteResumoDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoDTO {

    private Long id;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private EnderecoDTO enderecoEntrega;
    private String status;
    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataCancelamento;
    private OffsetDateTime dataEntrega;
    private FormaPagamentoDTO formaPagamento;
    private RestauranteResumoDTO restaurante;
    private UsuarioDTO cliente;
    private List<ItemPedidoDTO> itens;
}
