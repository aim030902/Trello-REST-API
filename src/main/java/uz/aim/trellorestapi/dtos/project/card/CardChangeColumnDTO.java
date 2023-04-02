package uz.aim.trellorestapi.dtos.project.card;

import lombok.*;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 14:27
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardChangeColumnDTO {
    private Long id;
    private Long boardColumnId;
}
