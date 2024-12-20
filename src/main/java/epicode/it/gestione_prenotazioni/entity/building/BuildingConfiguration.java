package epicode.it.gestione_prenotazioni.entity.building;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

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
        building.setCity(faker.address().cityName());
        return building;
    }
}
