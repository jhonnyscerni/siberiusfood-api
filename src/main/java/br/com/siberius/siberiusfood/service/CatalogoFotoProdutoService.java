package br.com.siberius.siberiusfood.service;

import br.com.siberius.siberiusfood.exception.FotoProdutoNaoEncontradaException;
import br.com.siberius.siberiusfood.model.FotoProduto;
import br.com.siberius.siberiusfood.repository.ProdutoRepository;
import br.com.siberius.siberiusfood.service.FotoStorageService.NovaFoto;
import java.io.InputStream;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FotoStorageService fotoStorageService;

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
        Long restauranteId = foto.getRestauranteId();
        Long produtoId = foto.getProduto().getId();
        String nomeNovoArquivo = fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo());
        String nomeArquivoExistente = null;

        Optional<FotoProduto> fotoProdutoExistente =
            produtoRepository.findFotoProdutoId(restauranteId, produtoId);

        if (fotoProdutoExistente.isPresent()) {
            nomeArquivoExistente = fotoProdutoExistente.get().getNomeArquivo();
            produtoRepository.delete(fotoProdutoExistente.get());
        }

        foto.setNomeArquivo(nomeNovoArquivo);
        foto = produtoRepository.save(foto);
        produtoRepository.flush();

        NovaFoto novaFoto = NovaFoto.builder()
            .nomeArquivo(foto.getNomeArquivo())
            .inputStream(dadosArquivo)
            .build();

//      Foi Abstraido no Service

//        if (nomeArquivoExistente != null) {
//            fotoStorageService.remover(nomeArquivoExistente);
//        }
//        fotoStorageService.armazenar(novaFoto);

        fotoStorageService.substituir(nomeArquivoExistente, novaFoto);

        return foto;
    }

    public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId) {
        return produtoRepository.findFotoProdutoId(restauranteId, produtoId)
            .orElseThrow(() -> new FotoProdutoNaoEncontradaException(restauranteId, produtoId));
    }

    @Transactional
    public void excluir(Long restauranteId, Long produtoId) {
        FotoProduto foto = buscarOuFalhar(restauranteId, produtoId);

        produtoRepository.delete(foto);
        produtoRepository.flush();

        fotoStorageService.remover(foto.getNomeArquivo());
    }
}
