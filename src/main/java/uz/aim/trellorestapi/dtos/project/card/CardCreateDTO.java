package uz.aim.trellorestapi.dtos.project.card;

import lombok.*;
import uz.aim.trellorestapi.dtos.project.base.BaseDTO;

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
public class CardCreateDTO implements BaseDTO {
    private String name;
    private Long boardColumnId;
}