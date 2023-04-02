package uz.aim.trellorestapi.controller.board;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.aim.trellorestapi.dtos.project.boardColumn.BoardColumnCreateDTO;
import uz.aim.trellorestapi.dtos.project.boardColumn.BoardColumnDTO;
import uz.aim.trellorestapi.dtos.project.boardColumn.BoardColumnOrderChangeDTO;
import uz.aim.trellorestapi.services.project.boardColumn.BoardColumnService;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 15:19
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@RestController
@RequestMapping("/api/boardColumn")
public class BoardColumnController {
    @Autowired
    private BoardColumnService boardColumnService;

    @PostMapping(value = "/create", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BoardColumnDTO> createBoardColumn(@RequestBody BoardColumnCreateDTO dto) {
        BoardColumnDTO boardColumnDto = boardColumnService.save(dto);
        return new ResponseEntity<>(boardColumnDto, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/changeOrder", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BoardColumnDTO> changeOrder(@RequestBody BoardColumnOrderChangeDTO dto) {
        BoardColumnDTO boardColumnDto = boardColumnService.changeOrder(dto);
        return new ResponseEntity<>(boardColumnDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteColumn/{id}")
    public ResponseEntity deleteColumn(@PathVariable Long id) {
        boardColumnService.deleteColumn(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}

