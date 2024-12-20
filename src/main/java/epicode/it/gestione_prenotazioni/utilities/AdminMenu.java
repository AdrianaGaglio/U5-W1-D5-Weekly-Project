package epicode.it.gestione_prenotazioni.utilities;

import epicode.it.gestione_prenotazioni.configuration.ColorConfiguration;
import epicode.it.gestione_prenotazioni.entity.building.BuildingService;
import epicode.it.gestione_prenotazioni.entity.reservation.ReservationService;
import epicode.it.gestione_prenotazioni.entity.station.StationService;
import epicode.it.gestione_prenotazioni.entity.user.UserService;

import java.util.Scanner;

public class AdminMenu {

    public static void showAdminMenu(Scanner scanner, UserService service, BuildingService buildingService, UserService userService, StationService stationService, ReservationService reservationService, ColorConfiguration.ConsoleColors consoleColors) throws Exception {
        while (true){
            System.out.println(consoleColors.YELLOW + " === Admin menu ===" + consoleColors.RESET);
            System.out.println(consoleColors.YELLOW + "1. Create new building" + consoleColors.RESET);
            System.out.println(consoleColors.YELLOW + "2. Create new station" + consoleColors.RESET);
            System.out.println(consoleColors.YELLOW + "3. Generate new reservation" + consoleColors.RESET);
            System.out.println(consoleColors.YELLOW + "4. Show all users" + consoleColors.RESET);
            System.out.println(consoleColors.YELLOW + "5. Show all buildings" + consoleColors.RESET);
            System.out.println(consoleColors.YELLOW + "6. Show all stations" + consoleColors.RESET);
            System.out.println(consoleColors.YELLOW + "7. Show all reservations" + consoleColors.RESET);
            System.out.println(consoleColors.YELLOW + "8. Show reservations by user" + consoleColors.RESET);
            System.out.println(consoleColors.YELLOW + "0. Exit" + consoleColors.RESET);
            System.out.println(consoleColors.YELLOW + "==================" + consoleColors.RESET);
            System.out.println(consoleColors.YELLOW + "Choose an option to proceed: " + consoleColors.RESET);
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 0) return;
            switch (choice) {
                case 1 -> MenuItems.createBuilding(scanner, buildingService, consoleColors);
                case 2 -> MenuItems.createStation(scanner, stationService, buildingService, consoleColors);
                case 3 ->
                        MenuItems.createReservation(scanner, userService, stationService, reservationService, consoleColors);
                case 4 -> MenuItems.showUsers(scanner, userService, consoleColors);
                case 5 -> MenuItems.showBuildings(scanner, buildingService, consoleColors);
                case 6 -> MenuItems.showStations(scanner, stationService, consoleColors);
                case 7 -> MenuItems.showReservations(scanner, reservationService, consoleColors);
                case 8 -> MenuItems.findByNameAndSurname(scanner, userService, reservationService, consoleColors);
                default -> {
                    System.out.println(consoleColors.RED + "Invalid input. Please try again." + consoleColors.RESET);
                }
            }
        }
    }
}
