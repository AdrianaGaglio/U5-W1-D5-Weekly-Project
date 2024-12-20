package epicode.it.gestione_prenotazioni.entity.station;

import com.github.javafaker.Faker;
import epicode.it.gestione_prenotazioni.entity.building.Building;
import epicode.it.gestione_prenotazioni.entity.building.BuildingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Order(3)
public class StationRunner implements ApplicationRunner {
    private final StationService stationService;
    private final BuildingService buildingService;
    private final Faker faker;


    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        if (stationService.count() == 0) {
            List<Building> buildings = buildingService.findAll();

            for (int i = 0; i < buildings.size(); i++) {
                Building b = buildings.get(i);
                // salvo 3 stanze private
                for (int j = 0; j < 3; j++) {
                    Station s = stationService.getNewStation(faker.number().numberBetween(2,10), Type.PRIVATE);
                    stationService.saveIntoBuilding(b, s);
                }
                // salvo 3 meeting room
                for (int j = 0; j < 3; j++) {
                    Station s = stationService.getNewStation(faker.number().numberBetween(2,10), Type.MEETING_ROOM);
                    stationService.saveIntoBuilding(b, s);
                }
                // salvo 3 stanze open space
                for (int j = 0; j < 3; j++) {
                    Station s = stationService.getNewStation(faker.number().numberBetween(2,10), Type.OPENSPACE);
                    stationService.saveIntoBuilding(b, s);
                }

            }
        }

    }
}
