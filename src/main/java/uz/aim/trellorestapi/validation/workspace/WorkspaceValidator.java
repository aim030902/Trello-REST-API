package uz.aim.trellorestapi.validation.workspace;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.aim.trellorestapi.configs.security.user.UserDetails;
import uz.aim.trellorestapi.domains.entity.auth.User;
import uz.aim.trellorestapi.domains.entity.workspace.Workspace;
import uz.aim.trellorestapi.domains.enums.auth.UserStatus;
import uz.aim.trellorestapi.domains.enums.workspace.WorkspaceType;
import uz.aim.trellorestapi.dtos.project.workspace.WorkspaceChangeVisibilityDTO;
import uz.aim.trellorestapi.dtos.project.workspace.WorkspaceCreateDTO;
import uz.aim.trellorestapi.dtos.project.workspace.WorkspaceMemberDTO;
import uz.aim.trellorestapi.dtos.project.workspace.WorkspaceUpdateDTO;
import uz.aim.trellorestapi.exception.GenericNotFoundException;
import uz.aim.trellorestapi.repository.auth.UserRepository;
import uz.aim.trellorestapi.repository.workspace.WorkspaceRepository;

import javax.validation.ValidationException;
import java.util.Objects;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 14:45
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Component
public class WorkspaceValidator {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkspaceRepository workspaceRepository;

    public void validateKey(Long id) throws ValidationException {
        workspaceRepository.findById(id).
                orElseThrow(() -> new ValidationException("Workspace not found by id : 5s".formatted(id)));
    }

    public void validOnCreate(WorkspaceCreateDTO dto) throws ValidationException {
        String name = dto.getName();
        WorkspaceType type = dto.getType();
        Long userId = dto.getUserId();
        if (Objects.isNull(name) || name.isBlank())
            throw new ValidationException("workspace name cannot be empty");
        if (Objects.isNull(type))
            throw new ValidationException("workspace type cannot be null");
        userRepository.findById(userId)
                .orElseThrow(() -> new ValidationException("User not found by id: %s".formatted(userId)));
    }

    public void validOnUpdate(WorkspaceUpdateDTO dto) throws ValidationException {

    }

    public void validOnAddMember(WorkspaceMemberDTO dto) throws ValidationException {
        Workspace workspace = workspaceRepository.findById(dto.getId()).
                orElseThrow(() -> new ValidationException("Workspace not found by id : %s".formatted(dto.getId())));
        String userEmail = dto.getMemberEmail();
        User authUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new GenericNotFoundException(
                        "user not found by email: %s".formatted(userEmail)));
        if (!authUser.isActive() || authUser.getStatus().equals(UserStatus.NOT_ACTIVE))
            throw new GenericNotFoundException("user not found by email: %s".formatted(userEmail));
        if (workspace.getCreatedBy().equals(authUser) || workspace.getMembers().contains(authUser))
            throw new IllegalArgumentException("already member");
    }

    public void validateOnChangeVisibility(WorkspaceChangeVisibilityDTO dto) {
        Workspace workspace = workspaceRepository.findById(dto.getId()).
                orElseThrow(() -> new ValidationException("Workspace not found by id : %s".formatted(dto.getId())));
        validateWorkspaceCreator(workspace);
    }

    public void validateWorkspaceCreator(Workspace workspace) {
        boolean isUserNotCreatorOfWorkspace = !workspace.getCreatedBy().getId().equals(getUserDetails().getId());
        if (isUserNotCreatorOfWorkspace)
            throw new ValidationException("You do not have permission to change visibility");
    }

    private UserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetails) authentication.getPrincipal();
    }

    public void validateOnRemoveMember(WorkspaceMemberDTO dto) {
        Workspace workspace = workspaceRepository.findById(dto.getId()).
                orElseThrow(() -> new ValidationException("Workspace not found by id : %s".formatted(dto.getId())));
        validateWorkspaceCreator(workspace);
        User authUser = userRepository.findByEmail(dto.getMemberEmail())
                .orElseThrow(() -> new GenericNotFoundException("User not found by email: " + dto.getMemberEmail()));
        if (!workspace.getMembers().contains(authUser))
            throw new ValidationException("User is not member of workspace with id: %s, user: %s ".formatted(dto.getId(), dto.getMemberEmail()));
    }
}

