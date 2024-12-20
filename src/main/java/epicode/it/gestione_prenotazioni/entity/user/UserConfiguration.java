package epicode.it.gestione_prenotazioni.entity.user;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class UserConfiguration {

    @Autowired
    private Faker faker;

    @Bean
    @Scope("prototype")
    public User getUser() {
        User user = new User();
        user.setUsername(faker.name().username());
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setEmail(faker.internet().emailAddress());
        return user;
    }
}
