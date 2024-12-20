package epicode.it.gestione_prenotazioni.entity.reservation;

import epicode.it.gestione_prenotazioni.entity.station.Station;
import epicode.it.gestione_prenotazioni.entity.station.StationService;
import epicode.it.gestione_prenotazioni.exceptions.MaxOccupantsException;
import epicode.it.gestione_prenotazioni.exceptions.NotAvailableException;
import epicode.it.gestione_prenotazioni.entity.user.User;
import epicode.it.gestione_prenotazioni.exceptions.NotFoundException;
import epicode.it.gestione_prenotazioni.exceptions.PastDateException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepo reservationRepo;
    private final StationService stationService;

    @Transactional
    public Reservation createNewReservation(User u, Station s, LocalDate date, int partecipants) throws NotAvailableException, MaxOccupantsException, NotFoundException, PastDateException {
        Reservation r = new Reservation();
        if(date.isBefore(LocalDate.now())) throw new PastDateException("Date cannot be in the past");
        Reservation foundReservation = findFirstByUserAndDate(u, date);
        r.setUser(u);
        Station station = stationService.findByMaxOccupants(s.getId(), partecipants);
        r.setStation(station);
        r.setParticipants(partecipants);
        r.setDate(date);
        r.setUser(u);
        station.getReservations().add(r);
        reservationRepo.save(r);
        return r;
    }

    public Reservation save(Reservation r) {
        return reservationRepo.save(r);
    }

    public List<Reservation> save(List<Reservation> reservations) {
        return reservationRepo.saveAll(reservations);
    }

    public List<Reservation> findAll() {
        return reservationRepo.findAll();
    }

    public int count() {
        return (int) reservationRepo.count();
    }

    public Reservation findById(Long id) throws NotFoundException {
        Reservation r = reservationRepo.findById(id).orElse(null);
        if (r == null) {
            throw new NotFoundException("No reservation found");
        }
        return r;
    }

    public List<Reservation> findByUserOrderByDateDesc(User user) throws NotFoundException {
        List<Reservation> reservations = reservationRepo.findByUserOrderByDateDesc(user);
        if (reservations.isEmpty()) {
            throw new NotFoundException("No reservations found for specified user");
        }
        return reservations;
    }

    public List<Reservation> findByStationOrderByIdAsc(Station station) throws NotFoundException {
        List<Reservation> reservations = reservationRepo.findByStationOrderByIdAsc(station);
        if (reservations.isEmpty()) {
            throw new NotFoundException("No reservation found for specified station");
        }
        return reservations;
    }

    public List<Reservation> findByDateBetween(LocalDate startDate, LocalDate endDate) throws NotFoundException {
        List<Reservation> reservations = reservationRepo.findByDateBetween(startDate, endDate);
        if (reservations.isEmpty()) {
            throw new NotFoundException("No reservation found for this dates range");
        }
        return reservations;
    }

    public Reservation findFirstByUserAndDate(User user, LocalDate date) throws NotAvailableException {
        Reservation found = reservationRepo.findFirstByUserAndDate(user, date).orElse(null);
        if (found != null) {
            throw new NotAvailableException("User already has a reservation for this date");
        }
        return found;
    }

    public Reservation delete(Reservation r) {
        reservationRepo.delete(r);
        return r;
    }

    public Reservation update(Reservation r) {
        return reservationRepo.save(r);
    }
}
