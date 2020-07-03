package br.com.siberius.siberiusfood.core.email;

import br.com.siberius.siberiusfood.infrastructure.service.email.FakeEnvioEmailService;
import br.com.siberius.siberiusfood.infrastructure.service.email.SmtpEnvioEmailService;
import br.com.siberius.siberiusfood.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EmailConfig {

    @Autowired
    private EmailProperties emailProperties;

    @Bean
    public EnvioEmailService envioEmailService() {
        // Acho melhor usar switch aqui do que if/else if
        switch (emailProperties.getImpl()) {
            case FAKE:
                return new FakeEnvioEmailService();
            case SMTP:
                return new SmtpEnvioEmailService();
            default:
                return null;
        }
    }

}