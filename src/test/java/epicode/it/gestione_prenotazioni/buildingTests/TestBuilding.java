package epicode.it.gestione_prenotazioni.buildingTests;

import epicode.it.gestione_prenotazioni.entity.building.Building;
import epicode.it.gestione_prenotazioni.entity.building.BuildingService;
import epicode.it.gestione_prenotazioni.exceptions.NotFoundException;
import epicode.it.gestione_prenotazioni.exceptions.UnavailableBuildingName;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestBuilding {

    @Autowired
    private BuildingService buildingService;

    @Test
    @Transactional
    @DisplayName("test building: creazione, ricerca e cancellazione")
    public void testCreateBuilding() throws UnavailableBuildingName, NotFoundException {
        Building b = new Building();
        b.setName("test 1");
        b.setAddress("via test 1");
        b.setCity("milano");
        b=buildingService.save(b);

        Building foundBuilding = buildingService.findById(b.getId());
        System.out.println("Building trovato => " + foundBuilding);
        assertEquals(b.getId(), foundBuilding.getId());
        assertEquals(b.getName(), foundBuilding.getName());
        assertEquals(b.getAddress(), foundBuilding.getAddress());
        assertEquals(b.getCity(), foundBuilding.getCity());

        Building deletedBuilding = buildingService.delete(foundBuilding.getId());
        Building notFoundBuilding = buildingService.findById(deletedBuilding.getId());
        assertNull(notFoundBuilding);
    }

    @Test
    @Transactional
    @DisplayName("test building: ricerca per nome e per città")
    public void testFindByName() throws UnavailableBuildingName, NotFoundException {
        Building b = new Building();
        b.setName("test 2");
        b.setAddress("via test 2");
        b.setCity("milano");
        b=buildingService.save(b);

        Building b2 = new Building();
        b2.setName("test 3");
        b2.setAddress("via test 3");
        b2.setCity("torino");
        b2=buildingService.save(b2);

        Building byName = buildingService.findByName("TEST 2");
        List<Building> likeName = buildingService.findByNameLike("est");
        List<Building> byCity = buildingService.findByCity("Milano");

        assertEquals(byCity.size(), 1);
        assertEquals(likeName.size(), 2);
        assertEquals(byName.getName(),b.getName());
    }
}
