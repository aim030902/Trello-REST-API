package uz.aim.trellorestapi.domains.entity.board;

import lombok.*;
import uz.aim.trellorestapi.domains.entity.workspace.Workspace;
import uz.aim.trellorestapi.domains.enums.board.BoardVisibility;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 14:12
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private BoardVisibility visibilityType;

    @Builder.Default
    @OneToMany(mappedBy = "board")
    private Set<BoardColumn> boardColumns = new HashSet<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", nullable = false, referencedColumnName = "id")
    private Workspace workspace;

    @Builder.Default
    @Column(columnDefinition = "bool default false")
    private Boolean isDeleted = false;
}

