package uz.aim.trellorestapi.dtos.project.boardColumn;

import lombok.*;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 14:25
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BoardColumnOrderChangeDTO {
    private Long id;
    private Integer order;
}