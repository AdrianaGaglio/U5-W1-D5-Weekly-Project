package epicode.it.gestione_prenotazioni.configuration;

import ch.qos.logback.classic.spi.LoggingEvent;
import epicode.it.gestione_prenotazioni.GestionePrenotazioniApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfiguration {

    @Bean
    public Logger getLogger() {
        return (Logger) LoggerFactory.getLogger(GestionePrenotazioniApplication.class);
    }
}
