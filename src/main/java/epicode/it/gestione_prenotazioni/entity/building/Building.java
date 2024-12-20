package epicode.it.gestione_prenotazioni.entity.building;

import epicode.it.gestione_prenotazioni.entity.station.Station;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name="buildings")
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private  Long id;

    private String name;

    private String address;

    private String city;

    @OneToMany(mappedBy = "building", cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.EAGER, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private Set<Station> stations = new LinkedHashSet<>();

    @Override
    public String toString() {
        return "Building{id=" + id + ", name='" + name + "', address='" + address + "', city='" + city + "'}";
    }

}