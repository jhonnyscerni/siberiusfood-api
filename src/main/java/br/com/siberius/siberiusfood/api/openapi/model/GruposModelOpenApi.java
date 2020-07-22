package br.com.siberius.siberiusfood.api.openapi.model;

import br.com.siberius.siberiusfood.api.model.GrupoDTO;
import io.swagger.annotations.ApiModel;
import java.util.List;
import lombok.Data;
import org.springframework.hateoas.Links;

@ApiModel("GruposModel")
@Data
public class GruposModelOpenApi {

    private GruposEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("GruposEmbeddedModel")
    @Data
    public class GruposEmbeddedModelOpenApi {

        private List<GrupoDTO> grupos;

    }
}