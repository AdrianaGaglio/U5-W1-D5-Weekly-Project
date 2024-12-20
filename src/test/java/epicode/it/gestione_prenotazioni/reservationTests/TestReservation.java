package epicode.it.gestione_prenotazioni.reservationTests;

import epicode.it.gestione_prenotazioni.entity.reservation.Reservation;
import epicode.it.gestione_prenotazioni.entity.reservation.ReservationService;
import epicode.it.gestione_prenotazioni.entity.station.Station;
import epicode.it.gestione_prenotazioni.entity.station.StationService;
import epicode.it.gestione_prenotazioni.exceptions.MaxOccupantsException;
import epicode.it.gestione_prenotazioni.exceptions.NotAvailableException;
import epicode.it.gestione_prenotazioni.entity.user.User;
import epicode.it.gestione_prenotazioni.entity.user.UserService;
import epicode.it.gestione_prenotazioni.exceptions.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class TestReservation {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserService userService;

    @Autowired
    private StationService stationService;

    @Autowired
    private Logger logger;

    @Test
    @DisplayName("Creazione prenotazione")
    public void testCreateReservation() throws MaxOccupantsException, NotAvailableException, NotFoundException {
        User u = userService.findById(1L);
        Station s = stationService.findById(1L);


        try {
        Reservation r = reservationService.createNewReservation(u, s, LocalDate.now(), 2);
        Reservation found = reservationService.findById(r.getId());
            System.out.println("=>>>>>>>> " + found);
        } catch (MaxOccupantsException | NotAvailableException e) {
            logger.error(e.getMessage());
        }


    }
}
