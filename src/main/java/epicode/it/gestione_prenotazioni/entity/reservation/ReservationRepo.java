package epicode.it.gestione_prenotazioni.entity.reservation;

import epicode.it.gestione_prenotazioni.entity.station.Station;
import epicode.it.gestione_prenotazioni.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {

    public List<Reservation> findByUserOrderByDateDesc(User user);

    public List<Reservation> findByStationOrderByIdAsc(Station station);

    public List<Reservation> findByDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT r FROM Reservation r WHERE r.user = :user AND r.date = :date")
    Optional<Reservation> findFirstByUserAndDate(@Param("user") User user, @Param("date") LocalDate date);


}
