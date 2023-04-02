package uz.aim.trellorestapi.configs.security.user;

import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import uz.aim.trellorestapi.domains.entity.auth.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 11:35
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Builder
public record UserDetails(User authUser) implements org.springframework.security.core.userdetails.UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (Objects.nonNull(authUser.getRoles()))
            authUser.getRoles().forEach(role -> {
                authorities.add(authority.apply(role.getAuthority()));
                if (Objects.nonNull(role.getPermissions()))
                    role.getPermissions().forEach(permission -> authorities.add(authority.apply(permission.name())));
            });
        return authorities;
    }

    @Override
    public String getPassword() {
        return authUser.getPassword();
    }

    @Override
    public String getUsername() {
        return authUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return authUser.isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return authUser.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return authUser.isActive();
    }

    @Override
    public boolean isEnabled() {
        return authUser.isActive();
    }

    private final static Function<String, SimpleGrantedAuthority> authority = SimpleGrantedAuthority::new;
    public Long getId() {
        return authUser.getId();
    }

}
