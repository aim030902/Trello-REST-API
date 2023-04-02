package uz.aim.trellorestapi.services.project.workspace;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.aim.trellorestapi.configs.security.user.UserDetails;
import uz.aim.trellorestapi.domains.entity.auth.User;
import uz.aim.trellorestapi.domains.entity.workspace.Workspace;
import uz.aim.trellorestapi.dtos.project.workspace.WorkspaceChangeVisibilityDTO;
import uz.aim.trellorestapi.dtos.project.workspace.WorkspaceCreateDTO;
import uz.aim.trellorestapi.dtos.project.workspace.WorkspaceDTO;
import uz.aim.trellorestapi.dtos.project.workspace.WorkspaceMemberDTO;
import uz.aim.trellorestapi.exception.GenericNotFoundException;
import uz.aim.trellorestapi.mapper.workspace.WorkspaceMapper;
import uz.aim.trellorestapi.repository.auth.UserRepository;
import uz.aim.trellorestapi.repository.workspace.WorkspaceRepository;
import uz.aim.trellorestapi.validation.workspace.WorkspaceValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 15:11
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Service
@Transactional
public class WorkspaceServiceImpl implements WorkspaceService {
    @Autowired
    private WorkspaceValidator workspaceValidator;
    @Autowired
    private WorkspaceMapper workspaceMapper;
    @Autowired
    private WorkspaceRepository workspaceRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public WorkspaceDTO get(Long id) throws IllegalAccessException {
        UserDetails userDetails = getUserDetails();
        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new GenericNotFoundException("workspace not found by id: %s".formatted(id)));
        boolean isNotCreator = !workspace.getCreatedBy().getId().equals(userDetails.getId());
        boolean isNotMember = !workspace.getMembers().contains(userDetails.authUser());
        if (isNotCreator && isNotMember) {
            throw new IllegalAccessException("You do not have permission to get workspace with id %s".formatted(id));
        }
        return workspaceMapper.fromWorkspace(workspace);
    }

    private UserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetails) authentication.getPrincipal();
    }

    @Override
    public WorkspaceDTO save(WorkspaceCreateDTO workspaceCreateDTO) {
        workspaceValidator.validOnCreate(workspaceCreateDTO);
        User authUser = userRepository.findById(workspaceCreateDTO.getUserId()).get();
        Workspace workspace = workspaceMapper.fromWorkspaceCreateDto(workspaceCreateDTO);
        workspace.setCreatedBy(authUser);
        return workspaceMapper.fromWorkspace(workspaceRepository.save(workspace));

    }

    @Override
    public void addMember(WorkspaceMemberDTO dto) {
        workspaceValidator.validOnAddMember(dto);
        String userEmail = dto.getMemberEmail();
        Workspace workspace = workspaceRepository.findById(dto.getId()).get();
        User authUser = userRepository.findByEmail(userEmail).get();
        workspace.getMembers().add(authUser);
        workspaceRepository.save(workspace);
    }

    @Override
    public void removeMember(WorkspaceMemberDTO dto) {
        workspaceValidator.validateOnRemoveMember(dto);
        Workspace workspace = workspaceRepository.findById(dto.getId())
                .orElseThrow(() -> new GenericNotFoundException("workspace not found by id: %s".formatted(dto.getId())));
        User authUser = userRepository.findByEmail(dto.getMemberEmail())
                .orElseThrow(() -> new GenericNotFoundException("User not found by email: " + dto.getMemberEmail()));
        workspace.getMembers().remove(authUser);
        workspaceRepository.save(workspace);
    }

    @Override
    public void changeVisibility(WorkspaceChangeVisibilityDTO dto) {
        workspaceValidator.validateOnChangeVisibility(dto);
        Workspace workspace = workspaceRepository.findById(dto.getId())
                .orElseThrow(() -> new GenericNotFoundException("workspace not found by id: %s".formatted(dto.getId())));
        workspace.setIsVisible(dto.getVisibility());
        workspaceRepository.save(workspace);
    }

    @Override
    public List<WorkspaceDTO> getAll() {
        UserDetails userDetails = getUserDetails();
        List<Workspace> workspaces = workspaceRepository.findAllByUser(userDetails.authUser());
        List<WorkspaceDTO> result = new ArrayList<>();
        for (Workspace workspace : workspaces) {
            WorkspaceDTO workspaceDTO = workspaceMapper.fromWorkspace(workspace);
            result.add(workspaceDTO);
        }
        return result;
    }
}

