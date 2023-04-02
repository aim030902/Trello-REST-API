package uz.aim.trellorestapi.dtos.project.workspace;

import lombok.*;
import uz.aim.trellorestapi.domains.enums.workspace.WorkspaceType;
import uz.aim.trellorestapi.dtos.project.base.BaseDTO;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 14:32
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class WorkspaceCreateDTO implements BaseDTO {
    private String name;
    private Long userId;
    private WorkspaceType type;
    private String description;
}