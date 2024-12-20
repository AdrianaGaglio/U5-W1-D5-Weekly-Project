package epicode.it.gestione_prenotazioni.utilities;

import epicode.it.gestione_prenotazioni.configuration.ColorConfiguration;
import epicode.it.gestione_prenotazioni.entity.reservation.ReservationService;
import epicode.it.gestione_prenotazioni.entity.station.StationService;
import epicode.it.gestione_prenotazioni.entity.user.UserService;

import java.util.Scanner;

public class UserMenu {

    public static void showUserMenu(Scanner scanner, StationService stationService, ReservationService reservationService, UserService userService, ColorConfiguration.ConsoleColors consoleColors) throws Exception {
        while (true){
            System.out.println(" === User menu ===");
            System.out.println("1. Create new user");
            System.out.println("2. Search station");
            System.out.println("0. Exit");
            System.out.println("==================");
            System.out.println("Choose an option to proceed: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 0) return;
            switch (choice) {
                case 1 -> Menu.createUser(scanner, userService, consoleColors);
                case 2 -> Menu.searchStation(scanner, stationService, reservationService, userService, consoleColors);
                default -> {
                    System.out.println(consoleColors.RED + "Invalid input. Please try again." + consoleColors.RESET);
                }
            }
        }
    }
}
