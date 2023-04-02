package uz.aim.trellorestapi.dtos.project.boardColumn;

import lombok.*;
import uz.aim.trellorestapi.dtos.project.base.BaseDTO;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 14:24
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BoardColumnCreateDTO implements BaseDTO {
    private String name;
    private Integer order;
    private Long boardId;
}