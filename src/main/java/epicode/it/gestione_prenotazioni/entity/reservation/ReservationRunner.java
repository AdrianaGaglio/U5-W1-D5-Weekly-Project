package epicode.it.gestione_prenotazioni.entity.reservation;

import com.github.javafaker.Faker;
import epicode.it.gestione_prenotazioni.entity.station.Station;
import epicode.it.gestione_prenotazioni.entity.station.StationService;
import epicode.it.gestione_prenotazioni.exceptions.MaxOccupantsException;
import epicode.it.gestione_prenotazioni.exceptions.NotAvailableException;
import epicode.it.gestione_prenotazioni.entity.user.User;
import epicode.it.gestione_prenotazioni.entity.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Order(5)
@RequiredArgsConstructor
public class ReservationRunner implements ApplicationRunner {
    private final ReservationService reservationService;
    private final StationService stationService;
    private final UserService userService;
    private final Faker faker;
    private final Logger logger;


    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        List<Station> stations = stationService.findAll();
        List<User> users = userService.findAll();

        for (Station s : stations) {
            int random = faker.number().numberBetween(0, users.size() - 1);
            User u = users.get(random);
            LocalDate date = LocalDate.now().plusDays(faker.number().numberBetween(1, 7));
            int partecipants = faker.number().numberBetween(1, 10);
            try {
                reservationService.createNewReservation(u, s, date, partecipants);
            } catch (NotAvailableException | MaxOccupantsException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
