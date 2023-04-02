package uz.aim.trellorestapi.domains.entity.auth;

import lombok.*;
import uz.aim.trellorestapi.domains.enums.auth.UserStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
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
@Table(name = "users")
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false)
    private String password;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status = UserStatus.NOT_ACTIVE;
    @ManyToMany(targetEntity = Role.class, cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;
    private int loginTryCount;
    private LocalDateTime lastLoginTime;
    private boolean deleted;

    public boolean isActive() {
        return UserStatus.ACTIVE.equals(this.status);
    }

}
