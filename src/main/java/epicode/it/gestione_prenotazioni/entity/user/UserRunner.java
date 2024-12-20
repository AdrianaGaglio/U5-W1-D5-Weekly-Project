package epicode.it.gestione_prenotazioni.entity.user;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@RequiredArgsConstructor
public class UserRunner implements ApplicationRunner {
    private final UserService userService;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (userService.count() == 0) {
            for (int i = 0; i < 10; i++) {
                userService.generateUser();
            }
        }
    }
}
