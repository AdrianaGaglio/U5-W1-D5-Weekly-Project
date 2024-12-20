package epicode.it.gestione_prenotazioni.entity.building;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(2)
public class BuildingRunner implements ApplicationRunner {
    private final BuildingService buildingService;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        if(buildingService.count() == 0) {
            for (int i = 0; i < 10; i++) {
                buildingService.createNewBuilding();
            }
        }

        System.out.println();
        System.out.println("Buildings list (total " + buildingService.count() + "):");
        buildingService.findAll().forEach(System.out::println);
        System.out.println();

    }
}
