package uz.aim.trellorestapi.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.aim.trellorestapi.domains.entity.auth.Role;
import uz.aim.trellorestapi.domains.entity.auth.User;
import uz.aim.trellorestapi.domains.enums.auth.Permission;
import uz.aim.trellorestapi.domains.enums.auth.RoleCode;
import uz.aim.trellorestapi.domains.enums.auth.UserStatus;
import uz.aim.trellorestapi.repository.auth.RoleRepository;
import uz.aim.trellorestapi.repository.auth.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 11:30
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;


    @Value("${spring.sql.init.mode}")
    private String initialMode;

    @Override
    public void run(String... args) throws Exception {
        if (initialMode.equals("always")) {
            Role roleAdmin = Role
                    .builder()
                    .name("ADMIN")
                    .code(RoleCode.ROLE_ADMIN)
                    .permissions(Set.of(Permission.values()))
                    .build();
            Role roleUser = Role
                    .builder()
                    .name("USER")
                    .code(RoleCode.ROLE_USER)
                    .permissions(Set.of(Permission.values()))
                    .build();

            User admin = User
                    .builder()
                    .email("admin@gmail.com")
                    .fullName("admin")
                    .password(passwordEncoder.encode("123"))
                    .roles(Set.of(roleAdmin, roleUser))
                    .status(UserStatus.ACTIVE)
                    .build();


//            User user = User
//                    .builder()
//                    .fullName("User")
//                    .username("user")
//                    .password(passwordEncoder.encode("123"))
//                    .email("user@gmail.com")
//                    .roles(Set.of(roleUser))
//                    .build();

            roleRepository.saveAll(new ArrayList<>(List.of(roleAdmin, roleUser)));
            userRepository.save(admin);
        }
    }
}