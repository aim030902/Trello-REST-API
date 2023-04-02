package uz.aim.trellorestapi.repository.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.aim.trellorestapi.domains.entity.board.Board;
import uz.aim.trellorestapi.domains.entity.board.Card;
import uz.aim.trellorestapi.domains.entity.workspace.Workspace;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 14:42
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    @Query("select w from Card c join c.boardColumn t join t.board b join b.workspace w where c.id=:cardId")
    Workspace findWorkspaceByCard(Long cardId);
}
