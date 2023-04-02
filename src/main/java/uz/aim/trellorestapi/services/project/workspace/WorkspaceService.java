package uz.aim.trellorestapi.services.project.workspace;

import uz.aim.trellorestapi.dtos.project.workspace.WorkspaceChangeVisibilityDTO;
import uz.aim.trellorestapi.dtos.project.workspace.WorkspaceCreateDTO;
import uz.aim.trellorestapi.dtos.project.workspace.WorkspaceDTO;
import uz.aim.trellorestapi.dtos.project.workspace.WorkspaceMemberDTO;

import java.util.List;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 15:10
 * Project : Trello-REST-API / IntelliJ IDEA
 */

public interface WorkspaceService {
    WorkspaceDTO save(WorkspaceCreateDTO workspaceCreateDTO);

    WorkspaceDTO get(Long id) throws IllegalAccessException;

    void addMember(WorkspaceMemberDTO dto);

    List<WorkspaceDTO> getAll();

    void changeVisibility(WorkspaceChangeVisibilityDTO dto);

    void removeMember(WorkspaceMemberDTO dto);
}
