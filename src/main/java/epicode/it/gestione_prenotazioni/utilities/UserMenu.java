package epicode.it.gestione_prenotazioni.utilities;

import epicode.it.gestione_prenotazioni.configuration.ColorConfiguration;
import epicode.it.gestione_prenotazioni.entity.reservation.ReservationService;
import epicode.it.gestione_prenotazioni.entity.station.StationService;
import epicode.it.gestione_prenotazioni.entity.user.UserService;

import java.util.Scanner;

public class UserMenu {

    public static void showUserMenu(Scanner scanner, StationService stationService, ReservationService reservationService, UserService userService, ColorConfiguration.ConsoleColors consoleColors) throws Exception {
        while (true) {
            System.out.println(consoleColors.YELLOW + " === User menu ===" + consoleColors.RESET);
            System.out.println(consoleColors.YELLOW + "1. Create new user" + consoleColors.RESET);
            System.out.println(consoleColors.YELLOW + "2. Search station" + consoleColors.RESET);
            System.out.println(consoleColors.YELLOW + "3. Find reservations" + consoleColors.RESET);
            System.out.println(consoleColors.YELLOW + "0. Exit" + consoleColors.RESET);
            System.out.println(consoleColors.YELLOW + "==================" + consoleColors.RESET);
            System.out.println(consoleColors.YELLOW + "Choose an option to proceed: " + consoleColors.RESET);
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 0) return;
            switch (choice) {
                case 1 -> MenuItems.createUser(scanner, userService, consoleColors);
                case 2 ->
                        MenuItems.searchStation(scanner, stationService, reservationService, userService, consoleColors);
                case 3 -> MenuItems.findReservations(scanner, userService, reservationService, consoleColors);
                default -> {
                    System.out.println(consoleColors.RED + "Invalid input. Please try again." + consoleColors.RESET);
                }
            }
        }
    }
}
