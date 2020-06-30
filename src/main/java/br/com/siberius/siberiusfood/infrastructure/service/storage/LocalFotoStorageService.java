package br.com.siberius.siberiusfood.infrastructure.service.storage;

import br.com.siberius.siberiusfood.core.storage.StorageProperties;
import br.com.siberius.siberiusfood.service.FotoStorageService;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

//@Service
public class LocalFotoStorageService implements FotoStorageService {

//    @Value("${siberius.storage.local.diretorio-fotos}")
//    private Path diretorioFotos;

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());

            FileCopyUtils.copy(novaFoto.getInputStream(),
                Files.newOutputStream(arquivoPath));
        } catch (Exception e) {
            throw new StorageException("Não foi possível armazenar arquivo.", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            Path arquivoPath = getArquivoPath(nomeArquivo);

            Files.deleteIfExists(arquivoPath);
        } catch (Exception e) {
            throw new StorageException("Não foi possível excluir arquivo.", e);
        }

    }

    private Path getArquivoPath(String nomeArquivo) {
//        return diretorioFotos.resolve(Paths.get(nomeArquivo));
        return storageProperties.getLocal().getDiretorioFotos().resolve(Paths.get(nomeArquivo));
    }

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        try {
            Path arquivoPath = getArquivoPath(nomeArquivo);

            FotoRecuperada fotoRecuperada = FotoRecuperada.builder()
                .inputStream(Files.newInputStream(arquivoPath))
                .build();

            return fotoRecuperada;
        } catch (Exception e) {
            throw new StorageException("Não foi possível recuperar arquivo.", e);
        }
    }

}