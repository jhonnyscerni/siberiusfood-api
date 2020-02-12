package br.com.siberius.siberiusfood.core.validation;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationConfig {

    @Bean
    public LocalValidatorFactoryBean validator(MessageSource messageSource){
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        /**
         * Nesse Momento estou unificando o messages.properties + ValidationMessages.properties
         * poise se comentarmos o codigo abaixo ele ira usar o messages.properties e ValidationMessages.properties
         * separadamente com suas respectivas ordens
         */
        bean.setValidationMessageSource(messageSource);
        return bean;
    }
}
