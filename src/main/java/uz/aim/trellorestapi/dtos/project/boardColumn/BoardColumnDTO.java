package uz.aim.trellorestapi.dtos.project.boardColumn;

import lombok.*;
import uz.aim.trellorestapi.dtos.project.card.CardDTO;

import java.util.List;

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
public class BoardColumnDTO {
    private Long id;
    private String name;
    private Integer order;
    private Long boardId;
    private List<CardDTO> cardSet;
}
