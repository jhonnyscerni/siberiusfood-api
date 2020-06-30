package br.com.siberius.siberiusfood.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FotoProdutoDTO {

    private String nomeArquivo;
    private String descricao;
    private String contentType;
    private Long tamanho;

}