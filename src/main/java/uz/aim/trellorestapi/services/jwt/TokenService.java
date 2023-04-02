package uz.aim.trellorestapi.services.jwt;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 12:08
 * Project : Trello-REST-API / IntelliJ IDEA
 */

public interface TokenService {
    String generateToken(UserDetails userDetails);

    boolean isValid(String token);

    default String getSubject(String token) {
        return null;
    }
}

