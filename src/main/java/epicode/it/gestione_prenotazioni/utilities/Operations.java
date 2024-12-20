package epicode.it.gestione_prenotazioni.utilities;

import epicode.it.gestione_prenotazioni.configuration.ColorConfiguration;
import epicode.it.gestione_prenotazioni.entity.building.Building;
import epicode.it.gestione_prenotazioni.entity.building.BuildingService;
import epicode.it.gestione_prenotazioni.entity.reservation.Reservation;
import epicode.it.gestione_prenotazioni.entity.reservation.ReservationService;
import epicode.it.gestione_prenotazioni.entity.station.Station;
import epicode.it.gestione_prenotazioni.entity.station.StationService;
import epicode.it.gestione_prenotazioni.entity.station.Type;
import epicode.it.gestione_prenotazioni.entity.user.User;
import epicode.it.gestione_prenotazioni.entity.user.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Operations {

    public static void editUser(Scanner scanner, UserService userService, User u, ColorConfiguration.ConsoleColors color) {
        System.out.println("New user email (empty if no changes are required)");
        String mail = scanner.nextLine();
        u.setEmail(mail.isEmpty() ? u.getEmail() : mail);
        System.out.println("New user first name (empty if no changes are required)");
        String name = scanner.nextLine();
        u.setFirstName(name.isEmpty() ? u.getFirstName() : name);
        System.out.println("New user last name (empty if no changes are required)");
        String surname = scanner.nextLine();
        u.setLastName(surname.isEmpty() ? u.getLastName() : surname);
        System.out.println("New user username (empty if no changes are required)");
        String username = scanner.nextLine();
        u.setLastName(username.isEmpty() ? u.getUsername() : username);
        User updated = userService.update(u);
        System.out.println(color.GREEN + "User info updated successfully: " + updated + color.RESET);
    }

    public static void deleteUser(UserService userService, User u, ColorConfiguration.ConsoleColors color) {
        userService.delete(u);
        System.out.println(color.GREEN + "User deleted successfully: " + u + color.RESET);
    }

    public static void editBuilding(Scanner scanner, BuildingService buildingService, Building b, ColorConfiguration.ConsoleColors color) {
        System.out.println("New building name (empty if no changes are required)");
        String name = scanner.nextLine();
        b.setName(name.isEmpty() ? b.getName() : name);
        System.out.println("New building address (empty if no changes are required)");
        String address = scanner.nextLine();
        b.setAddress(address.isEmpty() ? b.getAddress() : address);
        System.out.println("New building city (empty if no changes are required)");
        String city = scanner.nextLine();
        b.setCity(city.isEmpty() ? b.getCity() : city);
        Building updatedBuilding = buildingService.update(b);
        System.out.println(color.BLUE + "Building info updated: " + updatedBuilding + color.RESET);
    }

    public static void deleteBuilding(BuildingService buildingService, Building b, ColorConfiguration.ConsoleColors color) {
        buildingService.delete(b);
        System.out.println(color.GREEN + "Building deleted successfully: " + b + color.RESET);
    }

    public static void editStation(Scanner scanner, StationService stationService, Station s, ColorConfiguration.ConsoleColors color) {
        System.out.println("New station code (empty if no changes are required)");
        String code = scanner.nextLine();
        s.setCode(code.isEmpty() ? s.getCode() : code);
        System.out.println("New max occupants (empty if no changes are required)");
        Integer maxOccupants = null;
        maxOccupants = scanner.nextInt();
        scanner.nextLine();
        s.setMaxOccupants(maxOccupants==null ? s.getMaxOccupants() : maxOccupants);
        System.out.println("New station type 1-private 2-open space 3-meeting room (empty if no changes are required)");
        Integer typeInput = null;
        typeInput = scanner.nextInt();
        scanner.nextLine();
        Type type = null;
        switch (typeInput) {
            case 1 -> type = Type.PRIVATE;
            case 2 -> type = Type.OPENSPACE;
            case 3 -> type = Type.MEETING_ROOM;
            default -> System.out.println("Invalid input");
        }
        s.setType(type == null ? s.getType() : type);
        Station updatedStation = stationService.update(s);
        System.out.println(color.BLUE + "Station info updated: " + updatedStation + color.RESET);
    }

    public static void deleteStation(StationService stationService, Station s, ColorConfiguration.ConsoleColors color) {
        stationService.delete(s);
        System.out.println(color.GREEN + "Station deleted successfully: " + s + color.RESET);
    }

    public static void editReservation(Scanner scanner, ReservationService reservationService, Reservation r, ColorConfiguration.ConsoleColors color) {
        System.out.println("New reservation date (empty if no changes are required)");
        String inputDate = scanner.nextLine();
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(inputDate, inputFormatter);
        r.setDate(inputDate.isEmpty() ? r.getDate() : date);
        Reservation updatedReservation = reservationService.update(r);
        System.out.println(color.BLUE + "Reservation date updated: " + updatedReservation + color.RESET);
        return;
    }

    public static void deleteReservation(ReservationService reservationService, Reservation r, ColorConfiguration.ConsoleColors color) {
        reservationService.delete(r);
        System.out.println(color.GREEN + "Reservation deleted successfully: " + r + color.RESET);
    }

    public static LocalDate formatDate(Scanner scanner, ColorConfiguration.ConsoleColors color) throws Exception {
        String inputDate = scanner.nextLine();
        if (inputDate.isEmpty()) throw new Exception("Invalid input");
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(inputDate, inputFormatter);
        return date;
    }
}
