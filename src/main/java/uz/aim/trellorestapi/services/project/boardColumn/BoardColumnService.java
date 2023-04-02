package uz.aim.trellorestapi.services.project.boardColumn;

import uz.aim.trellorestapi.dtos.project.boardColumn.BoardColumnCreateDTO;
import uz.aim.trellorestapi.dtos.project.boardColumn.BoardColumnDTO;
import uz.aim.trellorestapi.dtos.project.boardColumn.BoardColumnOrderChangeDTO;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 15:06
 * Project : Trello-REST-API / IntelliJ IDEA
 */

public interface BoardColumnService {
    BoardColumnDTO save(BoardColumnCreateDTO dto);

    BoardColumnDTO changeOrder(BoardColumnOrderChangeDTO dto);

    void deleteColumn(Long id);
}
