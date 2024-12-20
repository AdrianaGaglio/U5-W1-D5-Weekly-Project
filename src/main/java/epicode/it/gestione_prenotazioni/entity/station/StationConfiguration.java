package epicode.it.gestione_prenotazioni.entity.station;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class StationConfiguration {
    @Autowired
    private Faker faker;

    @Bean
    @Scope("prototype")
    public Station getPrivateStation() {
        Station station = new Station();
        station.setCode(faker.code().asin());
        return station;
    }
}
