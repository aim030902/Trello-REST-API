package uz.aim.trellorestapi.dtos.oauth2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 11:09
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@AllArgsConstructor
@Getter
public class OAuth2UserDTO implements OAuth2User {
    private OAuth2User oauth2User;
    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oauth2User.getAuthorities();
    }

    @Override
    public String getName() {
//		System.out.println(oauth2User.<String>getAttribute("email"));
        return oauth2User.getAttribute("name");
    }

}
