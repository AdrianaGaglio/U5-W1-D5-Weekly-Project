package epicode.it.gestione_prenotazioni.entity.user;

import epicode.it.gestione_prenotazioni.entity.reservation.Reservation;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private  Long id;

    private String username;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    private String email;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @ToString.Exclude
    private List<Reservation> reservations;
}