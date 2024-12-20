package epicode.it.gestione_prenotazioni.entity.station;

import epicode.it.gestione_prenotazioni.entity.building.Building;
import epicode.it.gestione_prenotazioni.entity.reservation.Reservation;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="stations")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private  Long id;

    private String code;

    @Column(name="max_occupants")
    private int maxOccupants;

    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne
    @ToString.Exclude
    private Building building;

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @ToString.Exclude
    private List<Reservation> reservations = new ArrayList<>();
}