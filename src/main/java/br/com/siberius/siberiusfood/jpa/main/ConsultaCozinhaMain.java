package br.com.siberius.siberiusfood.jpa.main;

import br.com.siberius.siberiusfood.SiberiusfoodApiApplication;
import br.com.siberius.siberiusfood.model.Cozinha;
import br.com.siberius.siberiusfood.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ConsultaCozinhaMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(SiberiusfoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);

        List<Cozinha> todasCozinhas = cozinhaRepository.findAll();

        for (Cozinha cozinha : todasCozinhas) {
            System.out.println(cozinha.getNome());
        }
    }

}
