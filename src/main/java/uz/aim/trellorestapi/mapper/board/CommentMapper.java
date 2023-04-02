package uz.aim.trellorestapi.mapper.board;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.aim.trellorestapi.domains.entity.board.Comment;
import uz.aim.trellorestapi.dtos.project.comment.CommentCreateDTO;
import uz.aim.trellorestapi.dtos.project.comment.CommentDTO;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 14:38
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comment fromCreateDto(CommentCreateDTO dto);

    @Mapping(target = "creator", source = "creator.email")
    @Mapping(target = "cardId", source = "card.id")
    CommentDTO fromComment(Comment comment);
}
