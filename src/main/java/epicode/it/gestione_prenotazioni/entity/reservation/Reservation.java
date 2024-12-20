package epicode.it.gestione_prenotazioni.entity.reservation;

import epicode.it.gestione_prenotazioni.entity.station.Station;
import epicode.it.gestione_prenotazioni.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name="reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private  Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    private LocalDate date;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="station_id")
    private Station station;

    private int participants;

}