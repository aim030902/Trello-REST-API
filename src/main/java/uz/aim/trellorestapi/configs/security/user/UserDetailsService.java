package uz.aim.trellorestapi.configs.security.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.aim.trellorestapi.domains.entity.auth.User;
import uz.aim.trellorestapi.repository.auth.UserRepository;

import java.util.function.Supplier;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 11:42
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Supplier<UsernameNotFoundException> exception = () ->
                new UsernameNotFoundException("Bad credentials");
        User authUser = userRepository.findByEmail(username).orElseThrow(exception);
        return new UserDetails(authUser);

    }
}
