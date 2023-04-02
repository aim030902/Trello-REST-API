package uz.aim.trellorestapi.domains.entity.workspace;

import lombok.*;
import uz.aim.trellorestapi.domains.entity.auth.User;
import uz.aim.trellorestapi.domains.entity.board.Board;
import uz.aim.trellorestapi.domains.enums.workspace.WorkspaceType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 14:11
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Workspace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, referencedColumnName = "id", name = "created_by")
    private User createdBy;

    @Enumerated(EnumType.STRING)
    private WorkspaceType type;

    private String description;

    @Builder.Default
    private Boolean isVisible = true;

    @Builder.Default
    @OneToMany(mappedBy = "workspace", fetch = FetchType.LAZY)
    private Set<Board> boards = new HashSet<>();

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(joinColumns = @JoinColumn(name = "workspace_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> members = new HashSet<>();

    @Builder.Default
    @Column(columnDefinition = "bool default false")
    private Boolean isDeleted = false;
}

