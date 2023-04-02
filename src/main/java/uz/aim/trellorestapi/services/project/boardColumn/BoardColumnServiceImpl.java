package uz.aim.trellorestapi.services.project.boardColumn;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.aim.trellorestapi.domains.entity.board.Board;
import uz.aim.trellorestapi.domains.entity.board.BoardColumn;
import uz.aim.trellorestapi.dtos.project.boardColumn.BoardColumnCreateDTO;
import uz.aim.trellorestapi.dtos.project.boardColumn.BoardColumnDTO;
import uz.aim.trellorestapi.dtos.project.boardColumn.BoardColumnOrderChangeDTO;
import uz.aim.trellorestapi.exception.GenericNotFoundException;
import uz.aim.trellorestapi.mapper.board.BoardColumnMapper;
import uz.aim.trellorestapi.repository.board.BoardColumnRepository;
import uz.aim.trellorestapi.repository.board.BoardRepository;
import uz.aim.trellorestapi.validation.board.BoardColumnValidator;

import java.util.Set;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 15:06
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Service
@Transactional
public class BoardColumnServiceImpl implements BoardColumnService {
    @Autowired
    private BoardColumnRepository boardColumnRepository;
    @Autowired
    private BoardColumnValidator boardColumnValidator;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardColumnMapper boardColumnMapper;

    @Override
    public BoardColumnDTO save(BoardColumnCreateDTO dto) {
        boardColumnValidator.validOnCreate(dto);
        BoardColumn boardColumn = boardColumnMapper.fromCreateDto(dto);
        Board board = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new GenericNotFoundException("board not found by id: %s".formatted(dto.getBoardId())));
        boardColumn.setBoard(board);
        return boardColumnMapper.fromBoardColumn(boardColumnRepository.save(boardColumn));
    }

    @Override
    public BoardColumnDTO changeOrder(BoardColumnOrderChangeDTO dto) {
        boardColumnValidator.validateOnChangeOrder(dto);
        BoardColumn boardColumn = boardColumnRepository.findById(dto.getId())
                .orElseThrow(() -> new GenericNotFoundException(
                        "Board column not found by id: " + dto.getId()
                ));
        boardColumn.getBoard().getBoardColumns().stream()
                .filter(bc -> bc.getOrder() >= dto.getOrder())
                .forEach(nbc -> nbc.setOrder(nbc.getOrder() + 1));
        boardColumn.setOrder(dto.getOrder());
        return boardColumnMapper.fromBoardColumn(boardColumnRepository.save(boardColumn));
    }


    @Override
    public void deleteColumn(Long id) {
        boardColumnValidator.validateOnDelete(id);
        BoardColumn boardColumn = boardColumnRepository.findById(id)
                .orElseThrow(() -> new GenericNotFoundException(
                        "Board column not found by id: " + id
                ));
        Set<BoardColumn> boardColumns = boardColumn.getBoard()
                .getBoardColumns();
        boardColumns.stream()
                .filter(bc -> bc.getOrder() > boardColumn.getOrder())
                .forEach(nbc -> nbc.setOrder(nbc.getOrder() - 1));
        boardColumns.remove(boardColumn);
        boardColumn.setIsDeleted(true);
        boardColumnRepository.save(boardColumn);
    }
}

