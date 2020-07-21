package br.com.siberius.siberiusfood.api.openapi.model;


import br.com.siberius.siberiusfood.api.model.CidadeDTO;
import br.com.siberius.siberiusfood.api.model.CozinhaDTO;
import br.com.siberius.siberiusfood.api.openapi.model.CidadeCollectionModelOpenApi.CidadeEmbeddedModelOpenApi;
import io.swagger.annotations.ApiModel;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

@ApiModel("CozinhasModel")
@Setter
@Getter
public class CozinhasModelOpenApi {

    private CozinhasEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("CozinhasEmbeddedModel")
    @Data
    public class CozinhasEmbeddedModelOpenApi {

        private List<CozinhaDTO> cozinhas;

    }
}