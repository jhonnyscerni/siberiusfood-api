package br.com.siberius.siberiusfood;

import br.com.siberius.siberiusfood.infrastructure.repository.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class SiberiusfoodApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SiberiusfoodApiApplication.class, args);
    }

}
