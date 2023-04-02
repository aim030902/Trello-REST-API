package uz.aim.trellorestapi.repository.workspace;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.aim.trellorestapi.domains.entity.auth.User;
import uz.aim.trellorestapi.domains.entity.workspace.Workspace;

import java.util.List;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 14:44
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
    @Query(value = "select t from Workspace t where (t.createdBy =:user or :user member of t.members) and t.isDeleted=false")
    List<Workspace> findAllByUser(User user);
}