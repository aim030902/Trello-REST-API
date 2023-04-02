package uz.aim.trellorestapi.domains.entity.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import uz.aim.trellorestapi.domains.entity.auth.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 14:12
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;

    private String description;
    private LocalDateTime dueDate;

    @ManyToMany
    @Builder.Default
    @JoinTable(joinColumns = @JoinColumn(name = "card_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "auth_user_id", nullable = false))
    private final Set<User> members = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @Builder.Default
    private final List<Comment> comments = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "column_id", referencedColumnName = "id", nullable = false)
    private BoardColumn boardColumn;

    @Builder.Default
    @Column(columnDefinition = "bool default false")
    private Boolean isDeleted = false;

}

