package epicode.it.gestione_prenotazioni.entity.building;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class BuildingConfiguration {

    @Autowired
    private Faker faker;

    @Bean
    @Scope("prototype")
    public Building getBuilding() {
        Building building = new Building();
        building.setName(faker.company().name());
        building.setAddress(faker.address().streetAddress());
        building.setCity(cities().get(faker.number().numberBetween(0, cities().size() - 1)));
        return building;
    }

    public List<String> cities() {
        final List<String> cities = new ArrayList<>(List.of(
                "L'Aquila", "Potenza", "Catanzaro", "Napoli", "Bologna",
                "Trieste", "Roma", "Genova", "Milano", "Ancona",
                "Campobasso", "Torino", "Bari", "Cagliari", "Palermo",
                "Firenze", "Trento", "Perugia", "Aosta", "Venezia"
        ));
        return cities;
    }
}
