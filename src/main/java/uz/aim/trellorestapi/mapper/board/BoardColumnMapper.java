package uz.aim.trellorestapi.mapper.board;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.aim.trellorestapi.domains.entity.board.BoardColumn;
import uz.aim.trellorestapi.dtos.project.boardColumn.BoardColumnCreateDTO;
import uz.aim.trellorestapi.dtos.project.boardColumn.BoardColumnDTO;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 14:37
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Mapper(componentModel = "spring",
        uses = {CardMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BoardColumnMapper {
    BoardColumn fromCreateDto(BoardColumnCreateDTO dto);

    @Mapping(target = "boardId", source = "board.id")
    BoardColumnDTO fromBoardColumn(BoardColumn boardColumn);
}

