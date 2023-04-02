package uz.aim.trellorestapi.dtos.project.board;

import lombok.*;
import uz.aim.trellorestapi.domains.enums.board.BoardVisibility;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 14:21
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BoardChangeVisibilityDTO {
    private Long id;
    private BoardVisibility visibility;
}
