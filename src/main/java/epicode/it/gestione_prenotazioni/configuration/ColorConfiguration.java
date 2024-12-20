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
        public final String GREEN = "\033[0;32m";
        public final String YELLOW = "\033[0;33m";
        public final String PURPLE = "\033[0;35m";
        public final String CYAN = "\033[0;36m";
    }
}
