package epicode.it.gestione_prenotazioni.entity.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {

    public User findByUsernameIgnoreCase(String username);

    public User findByEmailIgnoreCase(String email);

    public List<User> findByFirstNameAndLastNameIgnoreCase(String firstName, String lastName);

    public User findFirstByEmailOrUsername(String email, String username);
}
