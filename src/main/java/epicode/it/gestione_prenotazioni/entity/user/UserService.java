package epicode.it.gestione_prenotazioni.entity.user;


import epicode.it.gestione_prenotazioni.entity.reservation.Reservation;
import epicode.it.gestione_prenotazioni.entity.station.Station;
import epicode.it.gestione_prenotazioni.entity.station.StationService;
import epicode.it.gestione_prenotazioni.exceptions.NotFoundException;
import epicode.it.gestione_prenotazioni.exceptions.UnavailableCode;
import epicode.it.gestione_prenotazioni.exceptions.UnavailableEmailAddressException;
import epicode.it.gestione_prenotazioni.exceptions.UnavailableUsername;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ObjectProvider<User> userProvider;
    private final UserRepo userRepo;
    private final StationService stationService;

    public User generateUser() {
        User newUser = userProvider.getObject();
        return userRepo.save(newUser);
    }

    public User save(User user) throws UnavailableUsername, UnavailableEmailAddressException {
        List<User> users = findAll();
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername())) {
                throw new UnavailableUsername("Username already exists");
            }
            if (u.getEmail().equals(user.getEmail())) {
                throw new UnavailableEmailAddressException("Email already exists");
            }
        }
        return userRepo.save(user);
    }

    public List<User> save(List<User> users) {
        return userRepo.saveAll(users);
    }

    public User findById(Long id) throws NotFoundException {
        User u = userRepo.findById(id).orElse(null);
        if (u == null) {
            throw new NotFoundException("User not found");
        }
        return u;
    }

    public User findByEmail(String email) throws NotFoundException {
        User u = userRepo.findByEmailIgnoreCase(email);
        if (u == null) {
            throw new NotFoundException("User not found");
        }
        return u;
    }

    public User findByUsername(String username) throws NotFoundException {
        User u = userRepo.findByUsernameIgnoreCase(username);
        if (u == null) {
            throw new NotFoundException("User not found");
        }
        return u;
    }

    public List<User> findByFirstNameAndLastName(String firstName, String lastName) {
        return userRepo.findByFirstNameAndLastNameIgnoreCase(firstName, lastName);
    }

    public User delete(Long id) throws NotFoundException {
        User user = findById(id);
        userRepo.delete(user);
        return user;
    }

    public User delete(User user) {
        User managedUser = userRepo.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        userRepo.delete(managedUser);

        return user;
    }

    public int count() {
        return (int) userRepo.count();
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public User findByEmailOrUsername(String email, String username) throws NotFoundException {
        User u = userRepo.findFirstByEmailOrUsername(email, username);
        if (u == null) throw new NotFoundException("User not found");
        return u;
    }

    public User update(User u) {
        return userRepo.save(u);
    }


}
