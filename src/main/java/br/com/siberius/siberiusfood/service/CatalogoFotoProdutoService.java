package br.com.siberius.siberiusfood.service;

import br.com.siberius.siberiusfood.model.FotoProduto;
import br.com.siberius.siberiusfood.repository.ProdutoRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public FotoProduto salvar(FotoProduto foto) {
        Long restauranteId = foto.getRestauranteId();
        Long produtoId = foto.getProduto().getId();

        Optional<FotoProduto> fotoProdutoExistente =
            produtoRepository.findFotoProdutoId(restauranteId, produtoId);

        if (fotoProdutoExistente.isPresent()) {
            produtoRepository.delete(fotoProdutoExistente.get());
        }

        return produtoRepository.save(foto);
    }
}
