package uz.aim.trellorestapi.dtos.project.board;

import lombok.*;
import uz.aim.trellorestapi.domains.enums.board.BoardVisibility;
import uz.aim.trellorestapi.dtos.project.boardColumn.BoardColumnDTO;

import java.util.Set;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 14:23
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDTO {
    private Long id;
    private String name;
    private BoardVisibility visibilityType;
    private Long workspaceId;
    private Set<BoardColumnDTO> boardColumns;
}
