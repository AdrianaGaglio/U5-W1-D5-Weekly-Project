package epicode.it.gestione_prenotazioni.entity.building;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildingRepo extends JpaRepository<Building, Long> {

    public Building findByNameIgnoreCase(String name);

    public List<Building> findByNameLikeIgnoreCase(String name);

    public List<Building> findByCityIgnoreCase(String city);
}
