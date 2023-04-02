package uz.aim.trellorestapi.configs.encryption;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 11:31
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Configuration
public class Encoder {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
