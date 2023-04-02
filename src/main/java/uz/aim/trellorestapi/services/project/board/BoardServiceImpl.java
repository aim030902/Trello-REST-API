package uz.aim.trellorestapi.services.project.board;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.aim.trellorestapi.domains.entity.board.Board;
import uz.aim.trellorestapi.domains.entity.workspace.Workspace;
import uz.aim.trellorestapi.dtos.project.board.BoardChangeVisibilityDTO;
import uz.aim.trellorestapi.dtos.project.board.BoardCreateDTO;
import uz.aim.trellorestapi.dtos.project.board.BoardDTO;
import uz.aim.trellorestapi.exception.GenericNotFoundException;
import uz.aim.trellorestapi.mapper.board.BoardMapper;
import uz.aim.trellorestapi.repository.board.BoardRepository;
import uz.aim.trellorestapi.repository.workspace.WorkspaceRepository;
import uz.aim.trellorestapi.validation.board.BoardValidator;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 15:03
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardValidator boardValidator;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private WorkspaceRepository workspaceRepository;
    @Autowired
    private BoardMapper boardMapper;

    @Override
    public BoardDTO save(BoardCreateDTO dto) {
        boardValidator.validOnCreate(dto);
        Board board = boardMapper.fromCreateDto(dto);
        Workspace workspace = workspaceRepository.findById(dto.getWorkspaceId())
                .orElseThrow(() -> new GenericNotFoundException("Workspace not found"));
        board.setWorkspace(workspace);
        Board savedBoard = boardRepository.save(board);
        return boardMapper.fromBoard(savedBoard);
    }

    @Override
    public BoardDTO get(Long id) {
        boardValidator.validateKey(id);
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new GenericNotFoundException("Board not found by id : %s".formatted(id)));
        return boardMapper.fromBoard(board);
    }

    @Override
    public void changeVisibility(BoardChangeVisibilityDTO dto) {
        boardValidator.validateOnChangeVisibility(dto);
        Board board = boardRepository.findById(dto.getId())
                .orElseThrow(() -> new GenericNotFoundException("Board not found by id : %s".formatted(dto.getId())));

        board.setVisibilityType(dto.getVisibility());
        boardRepository.save(board);
    }

    @Override
    public void deleteBoard(Long id) {
        boardValidator.validateOnDelete(id);
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new GenericNotFoundException(
                        "Board not found by id : %s".formatted(id))
                );

        board.setIsDeleted(true);
        boardRepository.save(board);
    }
}

