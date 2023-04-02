package uz.aim.trellorestapi.controller.workspace;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.aim.trellorestapi.dtos.project.workspace.WorkspaceChangeVisibilityDTO;
import uz.aim.trellorestapi.dtos.project.workspace.WorkspaceCreateDTO;
import uz.aim.trellorestapi.dtos.project.workspace.WorkspaceDTO;
import uz.aim.trellorestapi.dtos.project.workspace.WorkspaceMemberDTO;
import uz.aim.trellorestapi.services.project.workspace.WorkspaceService;

import javax.validation.Valid;
import java.util.List;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 15:14
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@RestController
@RequestMapping("/api/workspace")
@PreAuthorize("isAuthenticated()")
public class WorkspaceController {
    @Autowired
    private WorkspaceService workspaceService;

    @PostMapping(value = "/create", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<WorkspaceDTO> createWorkspace(@Valid @RequestBody WorkspaceCreateDTO workspaceCreateDto) {
        WorkspaceDTO workspaceDTO = workspaceService.save(workspaceCreateDto);
        return new ResponseEntity<>(workspaceDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<WorkspaceDTO>> getAll() {
        List<WorkspaceDTO> workspaceDTOs = workspaceService.getAll();
        return new ResponseEntity<>(workspaceDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<WorkspaceDTO> getWorkspace(@PathVariable Long id) throws IllegalAccessException {
        WorkspaceDTO workspaceDto = workspaceService.get(id);
        return new ResponseEntity<>(workspaceDto, HttpStatus.OK);
    }

    @PatchMapping(value = "/addMember")
    public ResponseEntity<Void> addMember(@Valid @RequestBody WorkspaceMemberDTO dto) {
        workspaceService.addMember(dto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/removeMember")
    public ResponseEntity removeMember(@Valid @RequestBody WorkspaceMemberDTO dto) {
        workspaceService.removeMember(dto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/changeVisibility")
    public ResponseEntity changeVisibility(@Valid @RequestBody WorkspaceChangeVisibilityDTO dto) {
        workspaceService.changeVisibility(dto);
        return ResponseEntity.noContent().build();
    }
}

