package uz.aim.trellorestapi.validation.board;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.aim.trellorestapi.configs.security.user.UserDetails;
import uz.aim.trellorestapi.domains.entity.board.Board;
import uz.aim.trellorestapi.domains.entity.workspace.Workspace;
import uz.aim.trellorestapi.domains.enums.board.BoardVisibility;
import uz.aim.trellorestapi.dtos.project.board.BoardChangeVisibilityDTO;
import uz.aim.trellorestapi.dtos.project.board.BoardCreateDTO;
import uz.aim.trellorestapi.exception.GenericNotFoundException;
import uz.aim.trellorestapi.repository.board.BoardRepository;
import uz.aim.trellorestapi.repository.workspace.WorkspaceRepository;

import javax.validation.ValidationException;
import java.util.Objects;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 14:52
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Component
public class BoardValidator {
    @Autowired
    private WorkspaceRepository workspaceRepository;
    @Autowired
    private BoardRepository boardRepository;

    public void validateKey(Long id) throws ValidationException {
        Board board = getBoard(id);
        validateAccessiblity(board);
    }

    private Board getBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new GenericNotFoundException("Board not found by id : %s".formatted(id)));
        if (board.getIsDeleted())
            throw new ValidationException("Board not available");
        return board;
    }

    public void validateAccessiblity(Board board) {
        BoardVisibility visibilityType = board.getVisibilityType();
        if (visibilityType.equals(BoardVisibility.PRIVATE)) {
            validateBoardWorkspace(board.getWorkspace());
        } else if (visibilityType.equals(BoardVisibility.WORKSPACE_MEMBERS)) {
            checkMembership(board);
        }
    }

    public void checkMembership(Board board) {
        boolean isNotMember = board.getWorkspace()
                .getMembers()
                .stream()
                .noneMatch(user -> user.getId().equals(getUserDetails().getId()));
        if (isNotMember)
            throw new ValidationException("you do not have permission on this workspace");
    }

    public void validOnCreate(BoardCreateDTO dto) throws ValidationException {
        String name = dto.getName();
        if (Objects.isNull(name) || name.isBlank()) {
            throw new ValidationException("board name cannot be empty");
        }
        Workspace workspace = workspaceRepository.findById(dto.getWorkspaceId())
                .orElseThrow(() -> new GenericNotFoundException("Workspace not found by id : %s".formatted(dto.getWorkspaceId())));
        validateBoardWorkspace(workspace);

    }

    private void validateBoardWorkspace(Workspace workspace) {
        boolean workspaceNotBelongsToCurrentUser = !workspace.getCreatedBy().getId().equals(getUserDetails().getId());
        if (workspace.getIsDeleted() || workspaceNotBelongsToCurrentUser) {
            throw new ValidationException("you do not have permission on this workspace");
        }
    }

    private UserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetails) authentication.getPrincipal();
    }

    public void validateOnChangeVisibility(BoardChangeVisibilityDTO dto) {
        Long id = dto.getId();
        Board board = getBoard(id);
        if (Objects.isNull(dto.getVisibility()))
            throw new ValidationException("Visibility type can not be null");
        validateBoardWorkspace(board.getWorkspace());
    }

    public void validateOnDelete(Long id) {
        Board board = getBoard(id);
        validateBoardWorkspace(board.getWorkspace());
    }
}