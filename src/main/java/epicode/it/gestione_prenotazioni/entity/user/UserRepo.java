package epicode.it.gestione_prenotazioni.entity.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {

    public User findByUsernameIgnoreCase(String username);

    public User findByEmailIgnoreCase(String email);

    @Query("SELECT u FROM User u WHERE LOWER(u.firstName) LIKE LOWER(CONCAT('%', :firstName, '%')) AND LOWER(u.lastName) LIKE LOWER(CONCAT('%', :lastName, '%'))")
    List<User> findByFirstNameAndLastNameLikeIgnoreCase(@Param("firstName") String firstName, @Param("lastName") String lastName);


    public User findFirstByEmailOrUsername(String email, String username);
}
