package epicode.it.gestione_prenotazioni.userTests;

import com.github.javafaker.Faker;
import epicode.it.gestione_prenotazioni.entity.user.User;
import epicode.it.gestione_prenotazioni.entity.user.UserService;
import epicode.it.gestione_prenotazioni.exceptions.NotFoundException;
import epicode.it.gestione_prenotazioni.exceptions.UnavailableEmailAddressException;
import epicode.it.gestione_prenotazioni.exceptions.UnavailableUsername;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestUser {

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    @DisplayName(("test utente: creazione, cancellazione e ricerca per id"))
    public void testCreateUser() throws UnavailableEmailAddressException, UnavailableUsername, NotFoundException {
        User u = new User();
        u.setUsername("username test");
        u.setFirstName("mario");
        u.setLastName("rossi");
        u.setEmail("mariorossi@mail.com");
        u = userService.save(u);

        User findUser = userService.findById(u.getId());
        System.out.println("Utente trovato => " + findUser);
        assertEquals(u.getId(), findUser.getId());
        assertEquals(u.getFirstName(), findUser.getFirstName());
        assertEquals(u.getLastName(), findUser.getLastName());
        assertEquals(u.getEmail(), findUser.getEmail());
        User deletedUser = userService.delete(findUser.getId());
        User notFoundUser = userService.findById(deletedUser.getId());
        assertNull(notFoundUser);
    }

    @Test
    @DisplayName("test utente: ricerca per email e username")
    public void testFindByEmailAndUsername() throws UnavailableEmailAddressException, UnavailableUsername, NotFoundException {
        User u1 = new User();
        u1.setUsername("usernametest1");
        u1.setFirstName("mario");
        u1.setLastName("rossi");
        u1.setEmail("mariorossi@mail.com");
        u1 = userService.save(u1);

        User u2 = new User();
        u2.setUsername("usernametest2");
        u2.setFirstName("giovanni");
        u2.setLastName("verdi");
        u2.setEmail("giovanniverdi@mail.com");
        u2 = userService.save(u2);

        User findUser1 = userService.findByEmail(u1.getEmail());
        User findUser2 = userService.findByUsername(u2.getUsername());

        System.out.println("Utente trovato per email => " + findUser1);
        System.out.println("Utente trovato per username => " + findUser2);

        assertEquals(u1.getEmail(), findUser1.getEmail());
        assertEquals(u2.getUsername(), findUser2.getUsername());
    }

    @Test
    @DisplayName("test utente: ricerca per nome e cognome")
    public void testFindByFirstNameAndLastName() throws UnavailableEmailAddressException, UnavailableUsername {
        User u1 = new User();
        u1.setUsername("usernametest1");
        u1.setFirstName("marco");
        u1.setLastName("rossi");
        u1.setEmail("mariorossi@mail.com");
        u1 = userService.save(u1);

        User u2 = new User();
        u2.setUsername("usernametest2");
        u2.setFirstName("marco");
        u2.setLastName("rossi");
        u2.setEmail("mr@mail.com");
        u2 = userService.save(u2);

        User u3 = new User();
        u3.setUsername("usernametest3");
        u3.setFirstName("giovanni");
        u3.setLastName("verdi");
        u3.setEmail("gv@mail.com");
        u3 = userService.save(u3);

        List<User> foundUsers = userService.findByFirstNameAndLastName("marco", "Rossi");


        assertEquals(foundUsers.size(), 2);
        assertTrue(foundUsers.contains(u1));
        assertTrue(foundUsers.contains(u2));
        assertFalse(foundUsers.contains(u3));


    }

}
