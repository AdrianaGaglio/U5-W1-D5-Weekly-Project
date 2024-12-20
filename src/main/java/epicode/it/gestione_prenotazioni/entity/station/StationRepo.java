package epicode.it.gestione_prenotazioni.entity.station;

import epicode.it.gestione_prenotazioni.entity.building.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StationRepo extends JpaRepository<Station, Long> {

    public Station findFirstByCode(String code);

    public List<Station> findByType(Type type);

    @Query("SELECT s FROM Station s WHERE LOWER(s.building.city) = LOWER(:city)")
    public List<Station> findByCityIgnoreCase(@Param("city") String city);

    @Query("SELECT s FROM Station s JOIN Building b ON s.building.id = b.id WHERE s.type = :type AND b.city = :city")
    public List<Station> findByTypeAndCity(Type type, String city);

    public List<Station> findByBuilding(Building building);

    @Query("SELECT s FROM Station s JOIN s.reservations r WHERE r.date = :date AND s.id = :id")
    public Station findByReservationDate(@Param("date") LocalDate date, @Param("id") Long id);


}
