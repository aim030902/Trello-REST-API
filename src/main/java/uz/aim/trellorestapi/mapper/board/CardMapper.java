package uz.aim.trellorestapi.mapper.board;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.aim.trellorestapi.domains.entity.board.Card;
import uz.aim.trellorestapi.dtos.project.card.CardCreateDTO;
import uz.aim.trellorestapi.dtos.project.card.CardDTO;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 14:37
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Mapper(componentModel = "spring", uses = {CommentMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CardMapper {
    @Mapping(target = "boardColumnId", source = "boardColumn.id")
    CardDTO fromCard(Card card);


    Card fromCreateDto(CardCreateDTO dto);
}
