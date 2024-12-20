package epicode.it.gestione_prenotazioni;

import epicode.it.gestione_prenotazioni.configuration.ColorConfiguration;
import epicode.it.gestione_prenotazioni.entity.building.BuildingService;
import epicode.it.gestione_prenotazioni.entity.reservation.ReservationService;
import epicode.it.gestione_prenotazioni.entity.station.StationService;
import epicode.it.gestione_prenotazioni.entity.user.User;
import epicode.it.gestione_prenotazioni.entity.user.UserService;
import epicode.it.gestione_prenotazioni.utilities.AdminMenu;
import epicode.it.gestione_prenotazioni.utilities.UserMenu;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@Order(10)
@RequiredArgsConstructor
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {
    private final UserService userService;
    private final BuildingService buildingService;
    private final StationService stationService;
    private final ReservationService reservationService;
    private final Scanner scanner;
    private final ColorConfiguration.ConsoleColors consoleColors;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        while(true) {
            try {
                System.out.println("=== Main menu ===");
                System.out.println("1- Admin menu");
                System.out.println("2- User menu");
                System.out.println("Select an option to proceed (0 to exit)");
                Integer choice = null;
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice == null) throw new Exception("Invalid input");
                if (choice == 0) return;
                switch (choice) {
                    case 1 -> AdminMenu.showAdminMenu(scanner, userService, buildingService,userService,stationService,reservationService,consoleColors);
                    case 2 -> UserMenu.showUserMenu(scanner, stationService, reservationService, userService, consoleColors);
                    default -> {
                        System.out.println(consoleColors.RED + "Invalid input. Please try again." + consoleColors.RESET);
                    }
                }
            } catch (Exception e) {
                System.out.println(consoleColors.RED + e.getMessage() + consoleColors.RESET);
            }
        }



    }
}
