package uz.aim.trellorestapi.dtos.project.comment;

import lombok.*;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 14:29
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentCreateDTO {
    private String creatorEmail;
    private String text;
    private String attachment;
    private Long cardId;
}
