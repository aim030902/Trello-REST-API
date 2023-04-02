package uz.aim.trellorestapi.services.project.board;

import uz.aim.trellorestapi.dtos.project.board.BoardChangeVisibilityDTO;
import uz.aim.trellorestapi.dtos.project.board.BoardCreateDTO;
import uz.aim.trellorestapi.dtos.project.board.BoardDTO;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 15:02
 * Project : Trello-REST-API / IntelliJ IDEA
 */

public interface BoardService {
    BoardDTO save(BoardCreateDTO dto);

    BoardDTO get(Long id);

    void changeVisibility(BoardChangeVisibilityDTO dto);

    void deleteBoard(Long id);
}
