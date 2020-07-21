package br.com.siberius.siberiusfood.api.controller;

import br.com.siberius.siberiusfood.api.SiberiusLinks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

    @Autowired
    private SiberiusLinks siberiusLinks;

    @GetMapping
    public RootEntryPointModel root() {
        RootEntryPointModel rootEntryPointModel = new RootEntryPointModel();

        rootEntryPointModel.add(siberiusLinks.linkToCozinhas("cozinhas"));
        rootEntryPointModel.add(siberiusLinks.linkToPedidos("pedidos"));
        rootEntryPointModel.add(siberiusLinks.linkToRestaurantes("restaurantes"));
        rootEntryPointModel.add(siberiusLinks.linkToGrupos("grupos"));
        rootEntryPointModel.add(siberiusLinks.linkToUsuarios("usuarios"));
        rootEntryPointModel.add(siberiusLinks.linkToPermissoes("permissoes"));
        rootEntryPointModel.add(siberiusLinks.linkToFormasPagamento("formas-pagamento"));
        rootEntryPointModel.add(siberiusLinks.linkToEstados("estados"));
        rootEntryPointModel.add(siberiusLinks.linkToCidades("cidades"));

        return rootEntryPointModel;
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {

    }

}