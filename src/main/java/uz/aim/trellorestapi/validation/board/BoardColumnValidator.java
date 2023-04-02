package uz.aim.trellorestapi.validation.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uz.aim.trellorestapi.domains.entity.board.Board;
import uz.aim.trellorestapi.domains.entity.board.BoardColumn;
import uz.aim.trellorestapi.dtos.project.boardColumn.BoardColumnCreateDTO;
import uz.aim.trellorestapi.dtos.project.boardColumn.BoardColumnOrderChangeDTO;
import uz.aim.trellorestapi.exception.GenericNotFoundException;
import uz.aim.trellorestapi.repository.board.BoardColumnRepository;
import uz.aim.trellorestapi.repository.board.BoardRepository;

import javax.validation.ValidationException;
import java.util.Objects;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 14:55
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Component
public class BoardColumnValidator {
    @Autowired
    private BoardValidator boardValidator;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardColumnRepository boardColumnRepository;

    public void validateKey(Long id) throws ValidationException {
        BoardColumn boardColumn = getBoardColumn(id);
        boardValidator.validateAccessiblity(boardColumn.getBoard());
    }

    protected BoardColumn getBoardColumn(Long id) {
        BoardColumn boardColumn = boardColumnRepository.findById(id).
                orElseThrow(() -> new GenericNotFoundException("Board column not found by id: %s".formatted(id)));
        if (boardColumn.getIsDeleted())
            throw new ValidationException("Board column not available with id: %s".formatted(id));
        return boardColumn;
    }

    public void validOnCreate(BoardColumnCreateDTO dto) throws ValidationException {
        String name = dto.getName();
        if (Objects.isNull(name) || name.isBlank()) {
            throw new ValidationException("Board column name can not be empty");
        }
        Board board = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new GenericNotFoundException("Board not found by id: %s".formatted(dto.getBoardId())));
        if (board.getIsDeleted())
            throw new ValidationException("Board not available");
        boardValidator.validateAccessiblity(board);

        int boardColumnsNumber = board.getBoardColumns().size();
        if (dto.getOrder() != (boardColumnsNumber + 1))
            throw new ValidationException("Order number don't matches sequence of board columns overall (%s) and order number: %s ".formatted(boardColumnsNumber, dto.getOrder()));
    }

    public void validateOnChangeOrder(BoardColumnOrderChangeDTO dto) {
        Long id = dto.getId();
        BoardColumn boardColumn = getBoardColumn(id);
        Board board = boardColumn.getBoard();
        boardValidator.validateAccessiblity(board);
        int boardColumnSize = board.getBoardColumns().size();
        if (dto.getOrder() < 1 || dto.getOrder() > boardColumnSize)
            throw new ValidationException("order number is not valid");

    }

    public void validateOnDelete(Long id) {
        BoardColumn boardColumn = getBoardColumn(id);
        boardValidator.validateAccessiblity(boardColumn.getBoard());
        boardValidator.checkMembership(boardColumn.getBoard());
    }


}
