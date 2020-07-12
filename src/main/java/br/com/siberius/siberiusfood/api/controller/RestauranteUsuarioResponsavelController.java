package br.com.siberius.siberiusfood.api.controller;

import br.com.siberius.siberiusfood.api.assembler.RestauranteDTOAssembler;
import br.com.siberius.siberiusfood.api.assembler.UsuarioAssembler;
import br.com.siberius.siberiusfood.api.model.RestauranteDTO;
import br.com.siberius.siberiusfood.api.model.UsuarioDTO;
import br.com.siberius.siberiusfood.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import br.com.siberius.siberiusfood.model.Restaurante;
import br.com.siberius.siberiusfood.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private UsuarioAssembler assembler;

    @GetMapping
    public List<UsuarioDTO> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);

        return assembler.getListUsuarioDTO(restaurante.getResponsaveis());
    }

    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteService.associarResponsavel(restauranteId, usuarioId);
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteService.desassociarResponsavel(restauranteId, usuarioId);
    }
}
