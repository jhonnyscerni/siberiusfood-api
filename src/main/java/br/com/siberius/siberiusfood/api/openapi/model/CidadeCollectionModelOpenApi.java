package br.com.siberius.siberiusfood.api.openapi.model;

import br.com.siberius.siberiusfood.api.model.CidadeDTO;
import io.swagger.annotations.ApiModel;
import java.util.List;
import lombok.Data;
import org.springframework.hateoas.Links;

@ApiModel("CidadesModel")
@Data
public class CidadeCollectionModelOpenApi {

    private CidadeEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("CidadesEmbeddedModel")
    @Data
    public class CidadeEmbeddedModelOpenApi {

        private List<CidadeDTO> cidades;

    }

}