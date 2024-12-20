package epicode.it.gestione_prenotazioni.configuration;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class FakerConfiguration {

    @Bean
    public Faker getFaker() {
        return new Faker(new Locale("it"));
    }
}
