package uz.aim.trellorestapi.services.project.card;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.aim.trellorestapi.domains.entity.auth.User;
import uz.aim.trellorestapi.domains.entity.board.BoardColumn;
import uz.aim.trellorestapi.domains.entity.board.Card;
import uz.aim.trellorestapi.domains.entity.board.Comment;
import uz.aim.trellorestapi.dtos.project.card.CardAddMemberDTO;
import uz.aim.trellorestapi.dtos.project.card.CardChangeColumnDTO;
import uz.aim.trellorestapi.dtos.project.card.CardCreateDTO;
import uz.aim.trellorestapi.dtos.project.card.CardDTO;
import uz.aim.trellorestapi.dtos.project.comment.CommentCreateDTO;
import uz.aim.trellorestapi.exception.GenericNotFoundException;
import uz.aim.trellorestapi.mapper.board.CardMapper;
import uz.aim.trellorestapi.mapper.board.CommentMapper;
import uz.aim.trellorestapi.repository.auth.UserRepository;
import uz.aim.trellorestapi.repository.board.BoardColumnRepository;
import uz.aim.trellorestapi.repository.board.CardRepository;
import uz.aim.trellorestapi.validation.board.CardValidator;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 15:08
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Service
@Transactional
public class CardServiceImpl implements CardService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardValidator cardValidator;
    @Autowired
    private CardMapper cardMapper;
    @Autowired
    private BoardColumnRepository boardColumnRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public CardDTO save(CardCreateDTO dto) {
        cardValidator.validOnCreate(dto);
        Card card = cardMapper.fromCreateDto(dto);
        BoardColumn boardColumn = boardColumnRepository.findById(dto.getBoardColumnId())
                .orElseThrow(() -> new GenericNotFoundException("Board column not found by id: " + dto.getBoardColumnId()));
        card.setBoardColumn(boardColumn);
        return cardMapper.fromCard(cardRepository.save(card));
    }

    @Override
    public CardDTO addComment(CommentCreateDTO dto) {
        cardValidator.validateOnAddComment(dto);
        Comment comment = commentMapper.fromCreateDto(dto);
        User authUser = userRepository.findByEmail(dto.getCreatorEmail())
                .orElseThrow(() -> new GenericNotFoundException("User not found by email: " + dto.getCreatorEmail()));
        comment.setCreator(authUser);
        Long cardId = dto.getCardId();
        Card card = getCard(cardId);
        comment.setCard(card);
        card.getComments().add(comment);
        return cardMapper.fromCard(cardRepository.save(card));
    }

    private Card getCard(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new GenericNotFoundException("Card not found by id: " + cardId));
        return card;
    }

    @Override
    public CardDTO addMember(CardAddMemberDTO dto) {
        cardValidator.validateOnAddMember(dto);
        Card card = getCard(dto.getId());
        User authUser = userRepository.findByEmail(dto.getMemberEmail())
                .orElseThrow(() -> new GenericNotFoundException("user not found by email: " + dto.getMemberEmail()));
        card.getMembers().add(authUser);
        return cardMapper.fromCard(cardRepository.save(card));
    }

    @Override
    public CardDTO changeColumn(CardChangeColumnDTO dto) {
        cardValidator.validateOnChangeColumn(dto);
        Card card = getCard(dto.getId());
        BoardColumn boardColumn = boardColumnRepository.findById(dto.getBoardColumnId())
                .orElseThrow(() -> new GenericNotFoundException("Board column not found by id: " + dto.getBoardColumnId()));
        card.setBoardColumn(boardColumn);
        return cardMapper.fromCard(cardRepository.save(card));
    }

    @Override
    public void deleteCard(Long id) {
        cardValidator.validateOnDelete(id);
        Card card = getCard(id);
        card.setIsDeleted(true);
        cardRepository.save(card);
    }
}

