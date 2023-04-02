package uz.aim.trellorestapi.validation.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uz.aim.trellorestapi.dtos.auth.RegisterDTO;
import uz.aim.trellorestapi.exception.GenericConflictException;
import uz.aim.trellorestapi.repository.auth.UserRepository;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 11:29
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Component
public class AuthValidation {
    @Autowired
    private UserRepository userRepository;
    public void validateOnRegister(RegisterDTO dto) {
        boolean isValid = userRepository.existsByEmail(dto.email());
        if (isValid) {
            throw new GenericConflictException("This is email already exists");
        }
    }
}
