package uz.aim.trellorestapi.configs.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.aim.trellorestapi.configs.security.user.UserDetails;
import uz.aim.trellorestapi.configs.security.user.UserDetailsService;
import uz.aim.trellorestapi.domains.entity.auth.User;
import uz.aim.trellorestapi.domains.enums.auth.UserStatus;
import uz.aim.trellorestapi.exception.GenericRuntimeException;
import uz.aim.trellorestapi.repository.auth.UserRepository;

import java.time.LocalDateTime;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 11:38
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Component
public class AuthManager implements AuthenticationProvider {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository repository;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        User authUser = userDetails.authUser();
        if (!encoder.matches(password, authUser.getPassword())) {
            String message;
            authUser.setLoginTryCount(authUser.getLoginTryCount() + 1);
            if (authUser.getLoginTryCount() == 3) {
                message = "You are blocked! Try 2 minutes later!";
                authUser.setStatus(UserStatus.BLOCKED);
                authUser.setLastLoginTime(LocalDateTime.now());
            } else {
                message = "Invalid username or password!";
            }
            repository.save(authUser);
            throw new GenericRuntimeException(message);
        }
        return new UsernamePasswordAuthenticationToken(new UserDetails(authUser), authentication);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

