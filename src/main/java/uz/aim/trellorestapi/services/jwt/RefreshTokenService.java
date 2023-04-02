package uz.aim.trellorestapi.services.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import uz.aim.trellorestapi.utils.jwt.JWTUtils;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 12:09
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Service
public class RefreshTokenService implements TokenService {
    private final JWTUtils jwtUtils;
    private final String secret;
    private final Integer amountToAdd;
    private final TemporalUnit timeUnit;

    public RefreshTokenService(@Lazy JWTUtils jwtUtils,
                               @Value("${jwt.refresh.token.secret}") String secret,
                               @Value("${jwt.refresh.token.expiry.adding.amount}") Integer amountToAdd,
                               @Value("${jwt.refresh.token.expiry.time.unit}") String timeUnitName) {
        this.jwtUtils = jwtUtils;
        this.secret = secret;
        this.amountToAdd = amountToAdd;
        this.timeUnit = ChronoUnit.valueOf(timeUnitName);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return jwtUtils.jwt(
                userDetails.getUsername(),
                secret,
                amountToAdd,
                timeUnit);
    }

    @Override
    public boolean isValid(String token) {
        return jwtUtils.isTokenValid(token, secret);
    }
    public String getSubject(String token) {
        return jwtUtils.getSubject(token, secret);
    }

}