package epicode.it.gestione_prenotazioni.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ColorConfiguration {

    @Bean
    public ConsoleColors consoleColors() {
        return new ConsoleColors();
    }

    public static class ConsoleColors {
        public final String RESET = "\u001B[0m";
        public final String RED = "\u001B[31m";
        public final String BLUE = "\u001B[34m";
    }
}
