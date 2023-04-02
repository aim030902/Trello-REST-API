package uz.aim.trellorestapi.dtos.project.workspace;

import lombok.*;
import uz.aim.trellorestapi.domains.enums.workspace.WorkspaceType;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 14:34
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class WorkspaceUpdateDTO {
    private Long id;
    private String name;
    private WorkspaceType type;
    private String description;
}
