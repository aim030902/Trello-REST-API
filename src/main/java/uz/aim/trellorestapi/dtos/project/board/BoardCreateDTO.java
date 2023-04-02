package uz.aim.trellorestapi.dtos.project.board;

import lombok.*;
import uz.aim.trellorestapi.domains.enums.board.BoardVisibility;
import uz.aim.trellorestapi.dtos.project.base.BaseDTO;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 14:22
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BoardCreateDTO implements BaseDTO {
    private String name;
    @Builder.Default
    private BoardVisibility visibilityType = BoardVisibility.PUBLIC;
    private Long workspaceId;
}
