package epicode.it.gestione_prenotazioni.stationTests;

import epicode.it.gestione_prenotazioni.entity.building.Building;
import epicode.it.gestione_prenotazioni.entity.building.BuildingService;
import epicode.it.gestione_prenotazioni.entity.station.Station;
import epicode.it.gestione_prenotazioni.entity.station.StationService;
import epicode.it.gestione_prenotazioni.entity.station.Type;
import epicode.it.gestione_prenotazioni.exceptions.NotFoundException;
import epicode.it.gestione_prenotazioni.exceptions.UnavailableCode;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestStation {

    @Autowired
    private StationService stationService;

    @Autowired
    private BuildingService buildingService;

    @Test
    @DisplayName("test station: creazione, cancellazione e ricerca per id")
    public void testCreateStation() throws UnavailableCode, NotFoundException {
        Station s = new Station();
        s.setCode("A1");
        s.setType(Type.PRIVATE);
        s.setMaxOccupants(10);
        s=stationService.save(s);

        Station foundStation = stationService.findById(s.getId());
        assertEquals(foundStation.getId(), s.getId());
        assertEquals(foundStation.getType(), s.getType());
        assertEquals(foundStation.getCode(), s.getCode());

        stationService.delete(foundStation.getId());

        Station notFoundStation = stationService.findById(foundStation.getId());

        assertNull(notFoundStation);

    }

    @Test
    @DisplayName("test station: ricerca per codice, tipo")
    public void testFindStation() throws UnavailableCode, NotFoundException {
        Station s1 = new Station();
        s1.setCode("A1");
        s1.setType(Type.PRIVATE);
        s1.setMaxOccupants(10);
        s1=stationService.save(s1);

        Station s2 = new Station();
        s2.setCode("A2");
        s2.setType(Type.OPENSPACE);
        s2.setMaxOccupants(10);
        s2=stationService.save(s2);

        Station s3 = new Station();
        s3.setCode("A3");
        s3.setType(Type.MEETING_ROOM);
        s3.setMaxOccupants(10);
        s3=stationService.save(s3);

        Station s4 = new Station();
        s4.setCode("A4");
        s4.setType(Type.MEETING_ROOM);
        s4.setMaxOccupants(10);
        s4=stationService.save(s4);

        Station s5 = new Station();
        s5.setCode("A5");
        s5.setType(Type.MEETING_ROOM);
        s5.setMaxOccupants(10);
        s5=stationService.save(s5);

        Station s6 = new Station();
        s6.setCode("A6");
        s6.setType(Type.OPENSPACE);
        s6.setMaxOccupants(10);
        s6=stationService.save(s6);

        Station found1 = stationService.findByCode("A1");
        Station found2 = stationService.findByCode("A2");
        Station found3 = stationService.findByCode("A3");

        List<Station> found4 = stationService.findByType(Type.PRIVATE);
        List<Station> found5 = stationService.findByType(Type.OPENSPACE);
        List<Station> found6 = stationService.findByType(Type.MEETING_ROOM);

        assertEquals(found1.getId(), s1.getId());
        assertEquals(found1.getCode(), s1.getCode());
        assertEquals(found1.getType(), s1.getType());
        assertEquals(found2.getId(), s2.getId());
        assertEquals(found2.getCode(), s2.getCode());
        assertEquals(found2.getType(), s2.getType());
        assertEquals(found3.getId(), s3.getId());
        assertEquals(found3.getCode(), s3.getCode());
        assertEquals(found3.getType(), s3.getType());

        assertTrue(found4.contains(found1));
        assertFalse(found4.contains(found2));
        assertFalse(found4.contains(found3));
        assertTrue(found5.contains(found2));
        assertFalse(found5.contains(found1));
        assertFalse(found5.contains(found3));
        assertTrue(found6.contains(found3));
        assertFalse(found6.contains(found1));
        assertFalse(found6.contains(found2));
    }

    @Test
    @Transactional
    @DisplayName("test station: abbinamento station-edificio, ricerca per tipo e città")
    public void testStationBuilding() throws NotFoundException, UnavailableCode {
        Building building = buildingService.findById(1L);

        Station s1 = new Station();
        s1.setCode("test1");
        s1.setType(Type.PRIVATE);
        s1.setMaxOccupants(10);

        Building savedBuildingAndStation = stationService.saveIntoBuilding(building, s1);

        List<Station> stationServiceByBuilding = stationService.findByBuilding(savedBuildingAndStation);
        Station foundStation = stationServiceByBuilding.stream().filter(s -> s.getCode() == "test1").findFirst().orElse(null);

        assertEquals(savedBuildingAndStation.getId(), foundStation.getBuilding().getId());

        List<Station> stationServiceByTypeAndCity = stationService.findByTypeAndCity(Type.PRIVATE, "Markstown");

        System.out.println(stationServiceByBuilding);

        assertTrue(stationServiceByBuilding.contains(foundStation));

    }
}
