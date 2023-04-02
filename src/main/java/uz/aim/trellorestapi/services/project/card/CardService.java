package uz.aim.trellorestapi.services.project.card;

import uz.aim.trellorestapi.dtos.project.card.CardAddMemberDTO;
import uz.aim.trellorestapi.dtos.project.card.CardChangeColumnDTO;
import uz.aim.trellorestapi.dtos.project.card.CardCreateDTO;
import uz.aim.trellorestapi.dtos.project.card.CardDTO;
import uz.aim.trellorestapi.dtos.project.comment.CommentCreateDTO;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 15:08
 * Project : Trello-REST-API / IntelliJ IDEA
 */

public interface CardService {
    CardDTO save(CardCreateDTO dto);

    CardDTO addMember(CardAddMemberDTO dto);

    CardDTO addComment(CommentCreateDTO dto);

    CardDTO changeColumn(CardChangeColumnDTO dto);

    void deleteCard(Long id);
}