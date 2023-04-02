package uz.aim.trellorestapi.domains.entity.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import uz.aim.trellorestapi.domains.entity.auth.User;

import javax.persistence.*;

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
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = User.class)
    @JoinColumn(nullable = false, referencedColumnName = "id", name = "created_by")
    private User creator;
    @Column(nullable = false)
    private String text;
    private String attachment;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false, name = "card_id", referencedColumnName = "id")
    private Card card;
}

