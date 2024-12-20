package epicode.it.gestione_prenotazioni.entity.station;

import epicode.it.gestione_prenotazioni.entity.building.Building;
import epicode.it.gestione_prenotazioni.entity.building.BuildingRepo;
import epicode.it.gestione_prenotazioni.exceptions.MaxOccupantsException;
import epicode.it.gestione_prenotazioni.exceptions.NotAvailableException;
import epicode.it.gestione_prenotazioni.exceptions.NotFoundException;
import epicode.it.gestione_prenotazioni.exceptions.UnavailableCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StationService {
    private final StationRepo stationRepo;
    private final BuildingRepo buildingRepo;
    private final ObjectProvider<Station> privateStationProvider;

    public Station getNewStation(int spaces, Type type) {
        Station s = privateStationProvider.getObject();
        s.setType(type);
        s.setMaxOccupants(spaces);
        return s;
    }

    public Station save(Station station) throws UnavailableCode {
        List<Station> stations = findAll();
        for(Station s : stations) {
            if (s.getCode().equalsIgnoreCase(station.getCode())) {
                throw new UnavailableCode("Code already in use");
            }
        }
        return stationRepo.save(station);
    }

    public List<Station> save(List<Station> stations) {
        return stationRepo.saveAll(stations);
    }

    @Transactional
    public Building saveIntoBuilding(Building b, Station s) throws NotFoundException, UnavailableCode {
        stationRepo.save(s);
        b.getStations().add(s);
        s.setBuilding(b);
        return buildingRepo.save(b);
    }

    public Station findById(Long id) throws NotFoundException {

        Station s = stationRepo.findById(id).orElse(null);
        if(s == null) {
            throw  new NotFoundException("No station found");
        }
        return s;
    }

    public List<Station> findByType(Type type) throws NotFoundException {
        List<Station> stations =  stationRepo.findByType(type);
        if(stations.isEmpty()) {
            throw new NotFoundException("No stations founds for this type");
        }
        return stations;
    }

    public Station findByCode(String code) {
        return stationRepo.findFirstByCode(code);
    }

    public int count() {
        return (int) stationRepo.count();
    }

    public List<Station> findAll() {
        return stationRepo.findAll();
    }

    public Station delete(Long id) throws NotFoundException {
        Station s = findById(id);
        stationRepo.delete(s);
        return s;
    }

    public Station delete(Station station) {
        Station managedStation = stationRepo.findById(station.getId()).orElseThrow(() -> new IllegalArgumentException("Building not found"));

        stationRepo.delete(managedStation);
        return station;
    }

    public List<Station> findByTypeAndCity(Type type, String city) throws NotFoundException {
       List<Station> stations = stationRepo.findByTypeAndCity(type, city);
       if(stations.isEmpty()) {
           throw new NotFoundException("No stations founds for this type and city");
       }
       return stations;
    }

    public List<Station> findByBuilding(Building b) throws NotFoundException {
        List<Station> stations = stationRepo.findByBuilding(b);
        if(stations.isEmpty()) {
            throw new NotFoundException("No stations founds for specified building");
        }
        return stations;
    }

    public Station findByReservationDate(LocalDate date, Long id) throws NotAvailableException {
        Station found = stationRepo.findByReservationDate(date, id);
        if(found == null) {
            return found;
        } else {
            throw new NotAvailableException("Station is already reserved for this date!");
        }
    }

    public Station findByMaxOccupants(Long id, int partecipants) throws MaxOccupantsException, NotFoundException {
        Station found = findById(id);
        if(found.getMaxOccupants() < partecipants) {
            throw new MaxOccupantsException("Not enough space in the station!");
        } else {
            return found;
        }
    }

    public List<Station> findByCity(String city) throws NotFoundException {
        List<Station> stations = stationRepo.findByCityIgnoreCase(city);
        if(stations == null) {
            throw new NotFoundException("No stations found for specified city");
        }
        return stations;
    }

    public Station update(Station station) {
        return stationRepo.save(station);
    }

}
