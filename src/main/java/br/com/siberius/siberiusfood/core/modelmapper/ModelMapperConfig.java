package br.com.siberius.siberiusfood.core.modelmapper;

import br.com.siberius.siberiusfood.api.model.EnderecoDTO;
import br.com.siberius.siberiusfood.model.Endereco;
import lombok.var;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper ModelMapper() {
        /**
         * Customizando o Mapeamento de propriedade com ModelMapper
         *
         * modelMapper.createTypeMap(Restaurante.class , RestauranteDTO.class)
         *         .addMapping(Restaurante::getTaxaFrete, RestauranteDTO::setPrecoFrete);
         */

        ModelMapper modelMapper = new ModelMapper();

        var enderecoToEnderecoDTOTypeMap =
                modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);

        enderecoToEnderecoDTOTypeMap.<String>addMapping(
                enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
                (enderecoDTODest, value) -> enderecoDTODest.getCidade().setEstado(value));

        return modelMapper;
    }
}
