package uz.aim.trellorestapi.dtos.project.boardColumn;

import lombok.*;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 14:26
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BoardColumnUpdateDTO {
    private Long id;
    private String name;
    private Integer order;
    private Long boardId;
}
