package br.com.siberius.siberiusfood.model;

import br.com.siberius.siberiusfood.event.PedidoCanceladoEvent;
import br.com.siberius.siberiusfood.event.PedidoConfirmadoEvent;
import br.com.siberius.siberiusfood.exception.NegocioException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.AbstractAggregateRoot;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
public class Pedido extends AbstractAggregateRoot<Pedido> {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;

    @Embedded
    private Endereco enderecoEntrega;

    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.CRIADO;

    @CreationTimestamp
    private OffsetDateTime dataCriacao;

    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataCancelamento;
    private OffsetDateTime dataEntrega;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private FormaPagamento formaPagamento;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurante restaurante;

    @ManyToOne
    @JoinColumn(name = "usuario_cliente_id", nullable = false)
    private Usuario cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens = new ArrayList<>();

    public void calcularValor() {
        this.subtotal = getItens().stream()
            .map(item -> item.getPrecoTotal())
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.valorTotal = this.subtotal.add(this.taxaFrete);
    }

    public void calcularValorTotal() {
        getItens().forEach(ItemPedido::calcularPrecoTotal);

        this.subtotal = getItens().stream()
            .map(item -> item.getPrecoTotal())
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.valorTotal = this.subtotal.add(this.taxaFrete);
    }

    public void definirFrete() {
        setTaxaFrete(restaurante.getTaxaFrete());
    }

    public void atribuirPedidoAosItens() {
        getItens().forEach(item -> item.setPedido(this));
    }

    public void confirmar() {
        setStatus(StatusPedido.CONFIRMADO);
        setDataConfirmacao(OffsetDateTime.now());
        registerEvent(new PedidoConfirmadoEvent(this));
    }

    public void cancelar() {
        setStatus(StatusPedido.CANCELADO);
        setDataCancelamento(OffsetDateTime.now());
        registerEvent(new PedidoCanceladoEvent(this));
    }

    public void entragar() {
        setStatus(StatusPedido.ENTREGUE);
        setDataEntrega(OffsetDateTime.now());
    }

    private void setStatus(StatusPedido novoStatus) {
        if (getStatus().naoPodeAlterarPara(novoStatus)) {
            throw new NegocioException(
                String.format("Status do pedido %d não pode ser alterado de %s para %s",
                    getCodigo(), getStatus().getDescricao(),
                    novoStatus.getDescricao()));
        }

        this.status = novoStatus;
    }

    @PrePersist
    private void gerarCodigo() {
        setCodigo(UUID.randomUUID().toString());
    }
}
