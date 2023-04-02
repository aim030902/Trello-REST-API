package uz.aim.trellorestapi.mapper.board;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.aim.trellorestapi.domains.entity.board.Board;
import uz.aim.trellorestapi.dtos.project.board.BoardCreateDTO;
import uz.aim.trellorestapi.dtos.project.board.BoardDTO;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 14:36
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Mapper(componentModel = "spring", uses = {CommentMapper.class, BoardColumnMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BoardMapper {
    Board fromCreateDto(BoardCreateDTO dto);

    @Mapping(target = "workspaceId", source = "workspace.id")
    BoardDTO fromBoard(Board board);
}

