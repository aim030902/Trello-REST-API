package uz.aim.trellorestapi.validation.board;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uz.aim.trellorestapi.domains.entity.auth.User;
import uz.aim.trellorestapi.domains.entity.board.Board;
import uz.aim.trellorestapi.domains.entity.board.BoardColumn;
import uz.aim.trellorestapi.domains.entity.board.Card;
import uz.aim.trellorestapi.domains.entity.workspace.Workspace;
import uz.aim.trellorestapi.dtos.project.card.CardAddMemberDTO;
import uz.aim.trellorestapi.dtos.project.card.CardChangeColumnDTO;
import uz.aim.trellorestapi.dtos.project.card.CardCreateDTO;
import uz.aim.trellorestapi.dtos.project.comment.CommentCreateDTO;
import uz.aim.trellorestapi.exception.GenericNotFoundException;
import uz.aim.trellorestapi.repository.auth.UserRepository;
import uz.aim.trellorestapi.repository.board.CardRepository;

import javax.validation.ValidationException;
import java.util.Objects;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 14:57
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Component
public class CardValidator {
    @Autowired
    private BoardColumnValidator boardColumnValidator;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private UserRepository userRepository;

    public void validateKey(Long id) throws ValidationException {
        Card card = getCard(id);
        boardColumnValidator.validateKey(card.getBoardColumn().getId());
    }

    private Card getCard(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new GenericNotFoundException("Card not found by id: %s".formatted(id)));
        if (card.getIsDeleted())
            throw new ValidationException("Card is not available with id: %s".formatted(id));
        return card;
    }

    public void validOnCreate(CardCreateDTO dto) throws ValidationException {
        boardColumnValidator.validateKey(dto.getBoardColumnId());
        String name = dto.getName();
        if (Objects.isNull(name) || name.isBlank())
            throw new ValidationException("Name can not be empty");

    }

    public void validateOnAddMember(CardAddMemberDTO dto) {
        validateKey(dto.getId());
        User user = userRepository.findByEmail(dto.getMemberEmail())
                .orElseThrow(() -> new GenericNotFoundException("user not found by email: " + dto.getMemberEmail()));
        Workspace workspaceByCard = cardRepository.findWorkspaceByCard(dto.getId());
        if (!workspaceByCard.getMembers().contains(user))
            throw new ValidationException("You can't add member outside your workspace");
    }

    public void validateOnAddComment(CommentCreateDTO dto) {
        validateKey(dto.getCardId());
        String text = dto.getText();
        if (Objects.isNull(text) || text.isBlank())
            throw new ValidationException(
                    "Comment text can not be empty"
            );
    }


    public void validateOnChangeColumn(CardChangeColumnDTO dto) {
        validateKey(dto.getId());
        Card card = getCard(dto.getId());
        Board board = card.getBoardColumn().getBoard();
        Long boardColumnId = dto.getBoardColumnId();
        BoardColumn boardColumn = boardColumnValidator.getBoardColumn(boardColumnId);
        boolean isColumnFromSameBoard = board.getBoardColumns().contains(boardColumn);
        if (!isColumnFromSameBoard) {
            throw new ValidationException(
                    "Board not contains column with id: " + boardColumnId
            );
        }
    }

    public void validateOnDelete(Long id) {
        Card card = getCard(id);
        boardColumnValidator.validateOnDelete(card.getBoardColumn().getId());
    }
}

