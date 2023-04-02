package uz.aim.trellorestapi.dtos.project.workspace;

import lombok.*;

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
public class WorkspaceChangeVisibilityDTO {
    private Long id;
    private Boolean visibility;
}
