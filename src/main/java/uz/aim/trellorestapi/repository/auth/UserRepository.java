package uz.aim.trellorestapi.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.aim.trellorestapi.domains.entity.auth.User;

import java.util.List;
import java.util.Optional;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 11:26
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String username);
    @Query(value = "from User where status = 'BLOCKED'")
    Optional<List<User>> getAllBlocked();
}
