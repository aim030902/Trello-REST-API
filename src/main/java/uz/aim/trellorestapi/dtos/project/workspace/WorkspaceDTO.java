package uz.aim.trellorestapi.dtos.project.workspace;

import lombok.*;
import uz.aim.trellorestapi.domains.enums.workspace.WorkspaceType;
import uz.aim.trellorestapi.dtos.project.board.BoardDTO;
import uz.aim.trellorestapi.dtos.user.UserDTO;

import java.util.Set;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 14:32
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkspaceDTO {
    private Long id;
    private String name;
    private Long createdBy;
    private WorkspaceType type;
    private String description;
    private Boolean isVisible;
    private Set<BoardDTO> boards;
    private Set<UserDTO> members;
}
