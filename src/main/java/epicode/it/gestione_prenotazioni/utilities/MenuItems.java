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
import epicode.it.gestione_prenotazioni.exceptions.*;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class MenuItems {

    public static void createUser(Scanner scanner, UserService userService, ColorConfiguration.ConsoleColors color) throws Exception {
        while (true) {
            System.out.println("Insert user first name (0 to exit):");
            String firstName = scanner.nextLine();
            if (firstName.equals("0")) break;
            System.out.println("Insert user last name (0 to exit):");
            String lastName = scanner.nextLine();
            if (lastName.equals("0")) break;
            System.out.println("Insert user username (0 to exit):");
            String username = scanner.nextLine();
            if (username.equals("0")) break;
            System.out.println("Insert user email (0 to exit):");
            String email = scanner.nextLine();
            if (email.equals("0")) break;
            if (username.isEmpty() || email.isEmpty() || firstName.isEmpty() || lastName.isEmpty())
                throw new Exception("Invalid input");
            try {
                User user = new User();
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setUsername(username);
                user.setEmail(email);
                user = userService.save(user);
                System.out.println(color.GREEN + "Created successfully: " + user + color.RESET);
                return;
            } catch (UnavailableUsername e) {
                System.out.println(color.RED + "===> " + e.getMessage() + color.RESET);
            } catch (Exception e) {
                System.out.println(color.RED + "===> " + e.getMessage() + color.RESET);
            }
        }
    }

    public static void createBuilding(Scanner scanner, BuildingService buildingService, ColorConfiguration.ConsoleColors color) throws Exception {
        while (true) {
            System.out.println("Insert building name (0 to exit):");
            String name = scanner.nextLine();
            if (name.equals("0")) break;
            System.out.println("Insert building city (0 to exit):");
            String city = scanner.nextLine();
            if (city.equals("0")) break;
            System.out.println("Insert full address (0 to exit):");
            String address = scanner.nextLine();
            if (address.equals("0")) break;
            if (name.isEmpty() || city.isEmpty() || address.isEmpty()) throw new Exception("Invalid input");
            try {
                Building building = new Building();
                building.setName(name);
                building.setCity(city);
                building.setAddress(address);
                building = buildingService.save(building);
                System.out.println(color.GREEN + "Created successfully: " + building + color.RESET);
                return;
            } catch (UnavailableBuildingName e) {
                System.out.println(color.RED + "===> " + e.getMessage() + color.RESET);
            } catch (Exception e) {
                System.out.println(color.RED + "===> " + e.getMessage() + color.RESET);
            }
        }
    }

    @Transactional
    public static void createStation(Scanner scanner, StationService stationService, BuildingService buildingService, ColorConfiguration.ConsoleColors color) throws NotFoundException, UnavailableCode {
        List<Building> buildings = buildingService.findAll();
        mainLoop:
        while (true) {
            System.out.println(color.BLUE + "List of available buildings:" + color.RESET);
            for (Building b : buildings) {
                System.out.println(color.BLUE + b + color.RESET);
            }
            System.out.println("Select building id to insert new station (0 to exit):");
            try {
                Long id = scanner.nextLong();
                scanner.nextLine(); // Consuma la nuova linea
                if (id == 0) break;
                Building b = buildingService.findById(id);
                System.out.println(color.BLUE + "Selected: " + b + color.RESET);

                Station s = new Station();

                System.out.println("Insert new station code (0 to exit):");
                String code = scanner.nextLine();
                if (code.equals("0")) break;

                Station checkStation = stationService.findByCode(code);
                if (checkStation != null) throw new UnavailableCode("Code already in use");
                s.setCode(code);
                System.out.println("Insert new station max occupants (0 to exit):");
                Integer maxOccupants = null;
                maxOccupants = scanner.nextInt();
                scanner.nextLine();
                if (maxOccupants == 0) break;
                s.setMaxOccupants(maxOccupants);

                System.out.println("Choose station type: 1-private, 2-meeting room, 3-open space (0 to exit):");
                int type = scanner.nextInt();
                scanner.nextLine();
                if (code.isEmpty() || maxOccupants == null)
                    throw new Exception("Invalid input");
                switch (type) {
                    case 1 -> s.setType(Type.PRIVATE);
                    case 2 -> s.setType(Type.MEETING_ROOM);
                    case 3 -> s.setType(Type.OPENSPACE);
                    case 0 -> {
                        System.out.println(color.RED + "===> Exiting..." + color.RESET);
                        break mainLoop;
                    }
                    default -> {
                        System.out.println(color.RED + "===> Invalid input. Please try again." + color.RESET);
                        continue;
                    }
                }

                stationService.saveIntoBuilding(b, s);
                System.out.println(color.GREEN + "Created successfully " + s + color.RESET);
                return;
            } catch (NotFoundException | UnavailableCode e) {
                System.out.println(color.RED + "===> " + e.getMessage() + color.RESET);
            } catch (InputMismatchException e) {
                System.out.println(color.RED + "===> Invalid input. Please enter a valid number." + color.RESET);
                scanner.nextLine(); // Pulisce il buffer dello scanner
            } catch (Exception e) {
                System.out.println(color.RED + "===> An unexpected error occurred: " + e.getMessage() + color.RESET);
            }
        }
    }

    public static void createReservation(Scanner scanner, UserService userService, StationService stationService, ReservationService reservationService, ColorConfiguration.ConsoleColors color) {
        List<User> users = userService.findAll();
        while (true) {
            for (User u : users) {
                System.out.println(color.BLUE + u + color.RESET);
            }
            System.out.println("Select user for new reservation (0 to exit):");
            Long id = scanner.nextLong();
            scanner.nextLine();
            if (id == 0) break;
            try {
                User u = userService.findById(id);
                System.out.println("Specified the city where you want to book a station (0 to exit):");
                String city = scanner.nextLine();
                if (city.equals(0)) break;
                if (city.isEmpty()) throw new Exception("Invalid input");
                if (stationService.findByCity(city).isEmpty())
                    throw new NotFoundException("There are no stations in this city");
                for (Station s : stationService.findByCity(city)) {
                    System.out.println(color.BLUE + s + color.RESET);
                }
                System.out.println("Select the station (0 to exit):");
                Long opt = null;
                opt = scanner.nextLong();
                scanner.nextLine();
                if (opt == null) throw new Exception("Invalid input");
                if (opt == 0) break;
                Station s = stationService.findById(opt);
                System.out.println(color.BLUE + "Selected: " + s + color.RESET);
                System.out.println("Insert number of partecipants (0 to exit):");
                Integer partecipants = null;
                partecipants = scanner.nextInt();
                scanner.nextLine();
                if (partecipants == null) throw new Exception("Invalid input");
                if (partecipants == 0) break;
                Reservation r = new Reservation();
                System.out.println("In which date you want to book the station: (0 to exit)?");
                LocalDate date = Operations.formatDate(scanner, color);
                Reservation reservation = reservationService.createNewReservation(u, s, date, partecipants);
                System.out.println(color.GREEN + "Created successfully: " + reservation + color.RESET);
                return;
            } catch (NotFoundException | PastDateException e) {
                System.out.println(color.RED + "===> " + e.getMessage() + color.RESET);
            } catch (Exception e) {
                System.out.println(color.RED + "===> " + e.getMessage() + color.RESET);
            }

        }
    }

    public static void showUsers(Scanner scanner, UserService userService, ColorConfiguration.ConsoleColors color) throws NotFoundException, Exception {
        while (true) {
            System.out.println("List of all users: " + userService.count());
            for (User u : userService.findAll()) {
                System.out.println(color.BLUE + u + color.RESET);
            }
            try {
                System.out.println("Select user to proceed (0 to exit):");
                Long id = null;
                id = scanner.nextLong();
                scanner.nextLine();
                if (id == null) throw new Exception("Invalid input");
                if (id == 0) break;
                User u = userService.findById(id);
                System.out.println(color.BLUE + "Selected: " + u + color.RESET);
                System.out.println("What do you want to do now?");
                System.out.println("1-Edit user info 2-Delete user 0-Exit");
                Integer opt = null;
                opt = scanner.nextInt();
                scanner.nextLine();
                if (opt == null) throw new Exception("Invalid input");
                if (opt == 0) break;
                switch (opt) {
                    case 1 -> {
                        Operations.editUser(scanner, userService, u, color);
                    }
                    case 2 -> {
                        Operations.deleteUser(userService, u, color);
                    }
                }
                return;
            } catch (NotFoundException e) {
                System.out.println(color.RED + "===> " + e.getMessage() + color.RESET);
            } catch (Exception e) {
                System.out.println(color.RED + "===> " + e.getMessage() + color.RESET);
            }
        }
    }

    public static void showBuildings(Scanner scanner, BuildingService buildingService, ColorConfiguration.ConsoleColors color) {
        while (true) {
            System.out.println(color.BLUE + "List of all buildings: " + buildingService.count() + color.RESET);
            for (Building b : buildingService.findAll()) {
                System.out.println(color.BLUE + b + color.RESET);
            }
            try {
                System.out.println("Select building to proceed (0 to exit):");
                Long id = null;
                id = scanner.nextLong();
                scanner.nextLine();
                if (id == null) throw new Exception("Invalid input");
                if (id == 0) break;
                Building b = buildingService.findById(id);
                System.out.println(color.BLUE + "Selected: " + b + color.RESET);
                System.out.println("What do you want to do now?");
                System.out.println("1-Edit building info 2-Delete building 0-Exit");
                Integer opt = null;
                opt = scanner.nextInt();
                scanner.nextLine();
                if (opt == null) throw new Exception("Invalid input");
                if (opt == 0) break;
                switch (opt) {
                    case 1 -> {
                        Operations.editBuilding(scanner, buildingService, b, color);
                    }
                    case 2 -> {
                        Operations.deleteBuilding(buildingService, b, color);
                    }
                }
                return;
            } catch (NotFoundException e) {
                System.out.println(color.RED + "===> " + e.getMessage() + color.RESET);
            } catch (Exception e) {
                System.out.println(color.RED + "===> " + e.getMessage() + color.RESET);
            }
        }
    }

    public static void showStations(Scanner scanner, StationService
            stationService, ColorConfiguration.ConsoleColors color) {
        while (true) {
            System.out.println(color.BLUE + "List of all stations: " + stationService.count() + color.RESET);
            for (Station s : stationService.findAll()) {
                System.out.println(color.BLUE + s + color.RESET);
            }
            try {
                System.out.println("Select station to proceed (0 to exit):");
                Long id = null;
                id = scanner.nextLong();
                scanner.nextLine();
                if (id == null) throw new Exception("Invalid input");
                if (id == 0) break;
                Station s = stationService.findById(id);
                System.out.println(color.BLUE + "Selected: " + s + color.RESET);
                System.out.println("What do you want to do now?");
                System.out.println("1-Edit station info 2-Delete station 0-Exit");
                Integer opt = null;
                opt = scanner.nextInt();
                scanner.nextLine();
                if (opt == null) throw new Exception("Invalid input");
                if (opt == 0) break;
                switch (opt) {
                    case 1 -> {
                        Operations.editStation(scanner, stationService, s, color);
                    }
                    case 2 -> {
                        Operations.deleteStation(stationService, s, color);
                    }
                }
                return;
            } catch (NotFoundException e) {
                System.out.println(color.RED + "===> " + e.getMessage() + color.RESET);
            } catch (Exception e) {
                System.out.println(color.RED + "===> " + e.getMessage() + color.RESET);
            }
        }
    }

    public static void showReservations(Scanner scanner, ReservationService
            reservationService, ColorConfiguration.ConsoleColors color) {
        while (true) {
            System.out.println(color.BLUE + "List of all reservations: " + reservationService.count() + color.RESET);
            for (Reservation r : reservationService.findAll()) {
                System.out.println(color.BLUE + r + color.RESET);
            }
            try {
                System.out.println("Select reservation to proceed (0 to exit):");
                Long id = null;
                id = scanner.nextLong();
                scanner.nextLine();
                if (id == null) throw new Exception("Invalid input");
                if (id == 0) break;
                Reservation r = reservationService.findById(id);
                System.out.println(color.BLUE + "Selected: " + r + color.RESET);
                System.out.println("What do you want to do now?");
                System.out.println("1-Edit reservation date 2-Delete reservation 0-Exit");
                Integer opt = null;
                opt = scanner.nextInt();
                scanner.nextLine();
                if (opt == null) throw new Exception("Invalid input");
                if (opt == 0) break;
                switch (opt) {
                    case 1 -> {
                        Operations.editReservation(scanner, reservationService, r, color);
                    }
                    case 2 -> {
                        Operations.deleteReservation(reservationService, r, color);
                    }
                }
                return;
            } catch (NotFoundException e) {
                System.out.println(color.RED + "===> " + e.getMessage() + color.RESET);
            } catch (Exception e) {
                System.out.println(color.RED + "===> " + e.getMessage() + color.RESET);
            }
        }
    }

    public static void searchStation(Scanner scanner, StationService stationService, ReservationService
            reservationService, UserService userService, ColorConfiguration.ConsoleColors color) {
        mainLoop:
        while (true) {
            System.out.println("Insert city where you want to book a station (0 to exit)");
            try {
                String city = scanner.nextLine();
                if (city.equals("0")) break;
                System.out.println("Which type of station are you searching for?");
                System.out.println("1-private 2-open space 3-meeting room");
                Integer typeInput = null;
                typeInput = scanner.nextInt();
                scanner.nextLine();
                if (typeInput == 0) break;
                if (typeInput == null || city.isEmpty()) throw new Exception("Invalid input");
                Type type = null;
                switch (typeInput) {
                    case 1 -> type = Type.PRIVATE;
                    case 2 -> type = Type.OPENSPACE;
                    case 3 -> type = Type.MEETING_ROOM;
                    case 0 -> {
                        System.out.println(color.RED + "===> Exiting..." + color.RESET);
                        break mainLoop;
                    }
                    default -> {
                        System.out.println(color.RED + "===> Invalid input. Please try again." + color.RESET);
                        continue;
                    }
                }
                for (Station s : stationService.findByTypeAndCity(type, city))
                    System.out.println(color.BLUE + s + color.RESET);
                System.out.println("Do you want to book a station? y/n (0 to exit)");
                String opt = scanner.nextLine();
                if (opt.equals("0")) break;
                if (opt.equals("y")) {
                    System.out.println("Select station by code (0 to exit)");
                    String code = scanner.nextLine();
                    if (code.isEmpty()) throw new Exception("Invalid input");
                    if (code.equals("0")) break mainLoop;
                    Station s = stationService.findByCode(code);
                    bookStation(scanner, reservationService, userService, color, s);
                    return;
                }
                return;
            } catch (Exception e) {
                System.out.println(color.RED + "===> " + e.getMessage() + color.RESET);
            }
        }
    }

    public static void bookStation(Scanner scanner, ReservationService reservationService, UserService
            userService, ColorConfiguration.ConsoleColors color, Station station) throws Exception {
        while (true) {
            try {
                System.out.println("Insert username or email for user:");
                String user = scanner.nextLine();
                if (user.equals("0")) break;
                if (user.isEmpty()) throw new Exception("Invalid input");
                User u = userService.findByEmailOrUsername(user, user);
                System.out.println("In which date you want to book the station: (0 to exit)?");
                LocalDate date = Operations.formatDate(scanner, color);
                System.out.println("Insert number of partecipants (0 to exit):");
                Integer partecipants = null;
                partecipants = scanner.nextInt();
                scanner.nextLine();
                if (partecipants == null) throw new Exception("Invalid input");
                if (partecipants == 0) break;
                Reservation r = reservationService.createNewReservation(u, station, date, partecipants);
                System.out.println(color.GREEN + "Saved successfully " + r + color.RESET);
                return;
            } catch (NotFoundException | PastDateException e) {
                System.out.println(color.RED + "===> " + e.getMessage() + color.RESET);
            } catch (Exception e) {
                System.out.println(color.RED + "===> " + e.getMessage() + color.RESET);
            }

        }
    }

    public static void findReservations(Scanner scanner, UserService userService, ReservationService reservationService, ColorConfiguration.ConsoleColors color) {
        while (true) {
            try {
                System.out.println("Insert username or email for user:");
                String user = scanner.nextLine();
                User u = userService.findByEmailOrUsername(user, user);
                List<Reservation> userReservations = reservationService.findByUserOrderByDateDesc(u);
                for (Reservation r : userReservations) {
                    System.out.println(color.BLUE + r + color.RESET);
                }
                System.out.println("Do you want to delete a reservation? y/n (0 to exit)");
                String opt = scanner.nextLine();
                if (opt.isEmpty()) throw new Exception("Invalid input");
                if (opt.equals("0")) break;
                if (opt.equals("y")) {
                    System.out.println("Select reservation to delete (0 to exit):");
                    Long id = null;
                    id = scanner.nextLong();
                    scanner.nextLine();
                    if (id == null) throw new Exception("Invalid input");
                    if (id == 0) break;
                    Reservation r = reservationService.findById(id);
                    reservationService.delete(r);
                    System.out.println(color.GREEN + "Deleted successfully " + r + color.RESET);
                }
                return;
            } catch (NotFoundException e) {
                System.out.println(color.RED + "===> " + e.getMessage() + color.RESET);
            } catch (Exception e) {
                System.out.println(color.RED + "===> " + e.getMessage() + color.RESET);
            }

        }
    }

    public static void findByNameAndSurname(Scanner scanner, UserService userService, ReservationService reservationService, ColorConfiguration.ConsoleColors color) {
        while (true) {
            try {
                System.out.println("Digit user first name:");
                String firstName = scanner.nextLine();
                System.out.println("Digit user last name:");
                String lastName = scanner.nextLine();
                if (firstName.isEmpty() || lastName.isEmpty()) throw new Exception("Invalid input");
                List<User> users = userService.findByFirstNameAndLastName(firstName, lastName);
                for (User u : users) {
                    System.out.println(color.BLUE + u + color.RESET);
                }
                System.out.println("Select user (0 to exit):");
                Long id = null;
                id = scanner.nextLong();
                scanner.nextLine();
                if (id == null) throw new Exception("Invalid input");
                if (id == 0) break;
                User u = userService.findById(id);
                for (Reservation r : reservationService.findByUserOrderByDateDesc(u)) {
                    System.out.println(color.BLUE + r + color.RESET);
                }
                System.out.println("What do you want to do now?");
                System.out.println("1-Edit reservation 2-Delete reservation 0-Exit");
                Integer opt = null;
                opt = scanner.nextInt();
                scanner.nextLine();
                if (opt == null) throw new Exception("Invalid input");
                if (opt == 0) break;
                System.out.println("Select reservation: (0 to exit)");
                Long resId = null;
                id = scanner.nextLong();
                scanner.nextLine();
                if (id == null) throw new Exception("Invalid input");
                if (id == 0) break;
                Reservation r = reservationService.findById(id);
                switch (opt) {
                    case 1 -> {
                        Operations.editReservation(scanner, reservationService, r, color);
                    }
                    case 2 -> {
                        Operations.deleteReservation(reservationService, r, color);
                    }
                }
                return;
            } catch (NotFoundException e) {
                System.out.println(color.RED + "===> " + e.getMessage() + color.RESET);
            } catch (Exception e) {
                System.out.println(color.RED + "===> " + e.getMessage() + color.RESET);
            }
        }
    }

}
