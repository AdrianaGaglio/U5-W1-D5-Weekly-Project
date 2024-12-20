package epicode.it.gestione_prenotazioni.entity.building;

import epicode.it.gestione_prenotazioni.entity.reservation.ReservationService;
import epicode.it.gestione_prenotazioni.entity.station.Station;
import epicode.it.gestione_prenotazioni.entity.station.StationService;
import epicode.it.gestione_prenotazioni.exceptions.NotFoundException;
import epicode.it.gestione_prenotazioni.exceptions.UnavailableBuildingName;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuildingService {
    private final ObjectProvider<Building> buildingProvider;
    private final BuildingRepo buildingRepo;
    private final ReservationService reservationService;
    private final StationService stationService;

    public Building createNewBuilding() {
        Building building = buildingProvider.getObject();
        return buildingRepo.save(building);
    }

    public Building save(Building building) throws UnavailableBuildingName {
        List<Building> buildings = findAll();
        for (Building b : buildings) {
            if (b.getName().equalsIgnoreCase(building.getName())) {
                throw new UnavailableBuildingName("Building name already exists");
            }
        }
        return buildingRepo.save(building);
    }

    public List<Building> save(List<Building> buildings) {
        return buildingRepo.saveAll(buildings);
    }

    public Building findById(Long id) throws NotFoundException {
        Building b = buildingRepo.findById(id).orElse(null);
        if (b == null) {
            throw new NotFoundException("Building not found");
        }
        return b;
    }

    public Building findByName(String name) throws NotFoundException {
        Building b = buildingRepo.findByNameIgnoreCase(name);
        if (b == null) {
            throw new NotFoundException("Building not found");
        }
        return b;
    }

    public List<Building> findByNameLike(String name) {
        return buildingRepo.findByNameLikeIgnoreCase("%" + name + "%");
    }

    public List<Building> findByCity(String city) throws NotFoundException {
        List<Building> buildings = buildingRepo.findByCityIgnoreCase(city);
        if (buildings.isEmpty()) {
            throw new NotFoundException("No buildings found for this city");
        }
        return buildings;
    }

    public int count() {
        return (int) buildingRepo.count();
    }

    public List<Building> findAll() {
        return buildingRepo.findAll();
    }

    public Building delete(Long id) throws NotFoundException {
        Building building = findById(id);
        buildingRepo.delete(building);
        return building;
    }

    public Building delete(Building building) {
        Building managedBuilding = buildingRepo.findById(building.getId())
                .orElseThrow(() -> new IllegalArgumentException("Building not found"));

        buildingRepo.delete(managedBuilding);
        return building;
    }


    public Building update(Building building) {
        return buildingRepo.save(building);
    }
}

