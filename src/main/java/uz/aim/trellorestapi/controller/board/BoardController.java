package uz.aim.trellorestapi.controller.board;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.aim.trellorestapi.dtos.project.board.BoardChangeVisibilityDTO;
import uz.aim.trellorestapi.dtos.project.board.BoardCreateDTO;
import uz.aim.trellorestapi.dtos.project.board.BoardDTO;
import uz.aim.trellorestapi.services.project.board.BoardService;

import javax.validation.Valid;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 15:17
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@RestController
@RequestMapping("/api/board")
@PreAuthorize("isAuthenticated()")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @PostMapping(value = "/create", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BoardDTO> createBoard(@RequestBody BoardCreateDTO dto) {
        BoardDTO boardDTO = boardService.save(dto);
        return new ResponseEntity<>(boardDTO, HttpStatus.CREATED);
    }

    @GetMapping(value = "/get/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BoardDTO> getBoard(@PathVariable Long id) {
        BoardDTO boardDTO = boardService.get(id);
        return ResponseEntity.ok(boardDTO);
    }


    @PatchMapping(value = "/changeVisibility")
    public ResponseEntity changeVisibility(@Valid @RequestBody BoardChangeVisibilityDTO dto) {
        boardService.changeVisibility(dto);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}

