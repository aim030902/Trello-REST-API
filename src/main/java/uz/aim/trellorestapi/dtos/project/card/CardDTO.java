package uz.aim.trellorestapi.dtos.project.card;

import lombok.*;
import uz.aim.trellorestapi.dtos.project.comment.CommentDTO;
import uz.aim.trellorestapi.dtos.user.UserDTO;

import java.util.List;
import java.util.Set;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 14:28
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardDTO {
    private Long id;
    private String name;
    private Set<UserDTO> members;
    //    private final List<Comment> comments = new ArrayList<>();
    private Long boardColumnId;
    private List<CommentDTO> comments;
}
