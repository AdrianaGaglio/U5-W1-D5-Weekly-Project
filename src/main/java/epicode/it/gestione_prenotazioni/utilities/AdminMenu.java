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
            System.out.println(" === Admin menu ===");
            System.out.println("1. Create new building");
            System.out.println("2. Create new station");
            System.out.println("3. Generate new reservation");
            System.out.println("4. Show all users");
            System.out.println("5. Show all buildings");
            System.out.println("6. Show all stations");
            System.out.println("7. Show all reservations");
            System.out.println("0. Exit");
            System.out.println("==================");
            System.out.println("Choose an option to proceed: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 0) return;
            switch (choice) {
                case 1 -> Menu.createBuilding(scanner, buildingService, consoleColors);
                case 2 -> Menu.createStation(scanner, stationService, buildingService, consoleColors);
                case 3 ->
                        Menu.createReservation(scanner, userService, stationService, reservationService, consoleColors);
                case 4 -> Menu.showUsers(scanner, userService, consoleColors);
                case 5 -> Menu.showBuildings(scanner, buildingService, consoleColors);
                case 6 -> Menu.showStations(scanner, stationService, consoleColors);
                case 7 -> Menu.showReservations(scanner, reservationService, consoleColors);
                default -> {
                    System.out.println(consoleColors.RED + "Invalid input. Please try again." + consoleColors.RESET);
                }
            }
        }
    }
}
