package uz.aim.trellorestapi.repository.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.aim.trellorestapi.domains.entity.board.Board;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 14:42
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}
