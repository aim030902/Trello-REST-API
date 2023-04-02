package uz.aim.trellorestapi.mapper.workspace;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.aim.trellorestapi.domains.entity.workspace.Workspace;
import uz.aim.trellorestapi.dtos.project.workspace.WorkspaceCreateDTO;
import uz.aim.trellorestapi.dtos.project.workspace.WorkspaceDTO;
import uz.aim.trellorestapi.dtos.project.workspace.WorkspaceMemberDTO;
import uz.aim.trellorestapi.dtos.project.workspace.WorkspaceUpdateDTO;
import uz.aim.trellorestapi.mapper.auth.UserMapper;
import uz.aim.trellorestapi.mapper.board.BoardMapper;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 14:40
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Mapper(componentModel = "spring", uses = {BoardMapper.class, UserMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface WorkspaceMapper {
    Workspace fromWorkspaceCreateDto(WorkspaceCreateDTO workspaceCreateDto);


    @Mapping(target = "createdBy", source = "createdBy.id")
    WorkspaceDTO fromWorkspace(Workspace workspace);

    WorkspaceUpdateDTO fromAddMemberToUpdate(WorkspaceMemberDTO dto);
}
