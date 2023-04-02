package uz.aim.trellorestapi.domains.entity.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

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
public class BoardColumn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "column_order")
    private Integer order;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Board.class)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Builder.Default
    @OneToMany(
            mappedBy = "boardColumn",
            cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY
    )
    private List<Card> cardSet = Collections.emptyList();
    @Builder.Default
    @Column(columnDefinition = "bool default false")
    private Boolean isDeleted = false;
}
