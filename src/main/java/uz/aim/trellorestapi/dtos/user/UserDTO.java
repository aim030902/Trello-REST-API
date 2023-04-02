package uz.aim.trellorestapi.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import uz.aim.trellorestapi.domains.enums.auth.UserStatus;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 11:12
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@AllArgsConstructor
@Getter
@Builder
public class UserDTO {
    private Long id;
    private String fullName;
    private String email;
    private String password;
    private UserStatus status;





}