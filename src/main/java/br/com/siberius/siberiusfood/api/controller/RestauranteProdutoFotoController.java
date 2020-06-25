package br.com.siberius.siberiusfood.api.controller;

import br.com.siberius.siberiusfood.api.model.input.FotoProdutoInput;
import java.util.UUID;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void atualizarFoto(@PathVariable Long restauranteId,
        @PathVariable Long produtoId, FotoProdutoInput fotoProdutoInput) {

        String nomeArquivo = UUID.randomUUID().toString()
            + "_" + fotoProdutoInput.getArquivo().getOriginalFilename();

        Path arquivoFoto = Paths.get("/home/jhonnycosta/Documentos/foto", nomeArquivo);

        System.out.println(fotoProdutoInput.getDescricao());
        System.out.println(arquivoFoto);
        System.out.println(fotoProdutoInput.getArquivo().getContentType());

        try {
            fotoProdutoInput.getArquivo().transferTo(arquivoFoto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}