package uz.aim.trellorestapi.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.aim.trellorestapi.domains.entity.auth.Role;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 11:25
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
