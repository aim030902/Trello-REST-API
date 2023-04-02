package uz.aim.trellorestapi.mapper.auth;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import uz.aim.trellorestapi.domains.entity.auth.User;
import uz.aim.trellorestapi.dtos.auth.RegisterDTO;
import uz.aim.trellorestapi.dtos.user.UserDTO;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 11:21
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toEntityFromRegisterDTO(RegisterDTO dto);
    RegisterDTO toRegisterDTOFromEntity(User user);
    UserDTO toUserDTOFromEntity(User entity);
}
