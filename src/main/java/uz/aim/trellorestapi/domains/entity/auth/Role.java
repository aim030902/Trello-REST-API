package uz.aim.trellorestapi.domains.entity.auth;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import uz.aim.trellorestapi.domains.enums.auth.Permission;
import uz.aim.trellorestapi.domains.enums.auth.RoleCode;

import javax.persistence.*;
import java.util.Set;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 10:58
 * Project : Trello-REST-API / IntelliJ IDEA
 */


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleCode code;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)

    private Set<Permission> permissions;
    private boolean deleted;

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name;
    }
}
