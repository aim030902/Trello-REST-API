package uz.aim.trellorestapi.controller.board;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.aim.trellorestapi.dtos.project.card.CardAddMemberDTO;
import uz.aim.trellorestapi.dtos.project.card.CardChangeColumnDTO;
import uz.aim.trellorestapi.dtos.project.card.CardCreateDTO;
import uz.aim.trellorestapi.dtos.project.card.CardDTO;
import uz.aim.trellorestapi.dtos.project.comment.CommentCreateDTO;
import uz.aim.trellorestapi.services.project.card.CardService;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 15:20
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@RestController
@RequestMapping("/api/card")
@PreAuthorize("isAuthenticated()")
public class CardController {
    @Autowired
    private CardService cardService;

    @PostMapping(value = "/create", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CardDTO> createBoardColumn(@RequestBody CardCreateDTO dto) {
        CardDTO cardDTO = cardService.save(dto);
        return new ResponseEntity<>(cardDTO, HttpStatus.CREATED);
    }

    @PostMapping(value = "/addMember")
    public ResponseEntity<CardDTO> addMember(@RequestBody CardAddMemberDTO dto) {
        CardDTO cardDTO = cardService.addMember(dto);
        return new ResponseEntity<>(cardDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/comment/create")
    public ResponseEntity<CardDTO> addComment(@RequestBody CommentCreateDTO dto) {
        CardDTO cardDTO = cardService.addComment(dto);
        return new ResponseEntity<>(cardDTO, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/changeColumn")
    public ResponseEntity<CardDTO> changeColumn(@RequestBody CardChangeColumnDTO dto) {
        CardDTO cardDTO = cardService.changeColumn(dto);
        return new ResponseEntity<>(cardDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

