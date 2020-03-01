package br.com.siberius.siberiusfood.core.modelmapper;

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
        return modelMapper;
    }
}
