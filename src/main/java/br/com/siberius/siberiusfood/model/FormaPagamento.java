package br.com.siberius.siberiusfood.model;

import java.time.OffsetDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class FormaPagamento {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @UpdateTimestamp
    private OffsetDateTime dataAtualizacao;
}
