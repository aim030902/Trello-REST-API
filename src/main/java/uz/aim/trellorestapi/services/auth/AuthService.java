package uz.aim.trellorestapi.services.auth;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.aim.trellorestapi.configs.security.auth.AuthManager;
import uz.aim.trellorestapi.domains.entity.auth.ActivationCode;
import uz.aim.trellorestapi.domains.entity.auth.Role;
import uz.aim.trellorestapi.domains.entity.auth.User;
import uz.aim.trellorestapi.domains.enums.auth.UserStatus;
import uz.aim.trellorestapi.dtos.auth.LoginDTO;
import uz.aim.trellorestapi.dtos.auth.RegisterDTO;
import uz.aim.trellorestapi.dtos.jwt.JwtResponseDTO;
import uz.aim.trellorestapi.dtos.jwt.RefreshTokenDTO;
import uz.aim.trellorestapi.dtos.response.ApiResponse;
import uz.aim.trellorestapi.dtos.user.UserDTO;
import uz.aim.trellorestapi.exception.GenericInvalidTokenException;
import uz.aim.trellorestapi.exception.GenericNotFoundException;
import uz.aim.trellorestapi.exception.GenericRuntimeException;
import uz.aim.trellorestapi.mapper.auth.UserMapper;
import uz.aim.trellorestapi.repository.auth.RoleRepository;
import uz.aim.trellorestapi.repository.auth.UserRepository;
import uz.aim.trellorestapi.services.jwt.TokenService;
import uz.aim.trellorestapi.services.mail.MailService;
import uz.aim.trellorestapi.validation.auth.AuthValidation;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 12:12
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Service
public class AuthService {
    @Value("${activation.link.base.path}")
    private String basePath;
    @Autowired
    private AuthValidation authValidation;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ActivationCodeService activationCodeService;
    @Autowired
    private MailService mailService;
    @Autowired
    private AuthManager authenticationManager;
    @Autowired
    @Qualifier("accessTokenService")
    private TokenService accessTokenService;
    @Autowired
    @Qualifier("refreshTokenService")
    private TokenService refreshTokenService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserDetailsService userDetailsService;

    @SneakyThrows
    @Transactional
    public ApiResponse<User> register(@NonNull RegisterDTO dto) {
        authValidation.validateOnRegister(dto);
        User createUser = userMapper.toEntityFromRegisterDTO(dto);
        Role roleUser = roleRepository.findByName("USER");
        createUser.setPassword(passwordEncoder.encode(createUser.getPassword()));
        createUser.setRoles(Set.of(roleUser));
        User savedUser = userRepository.save(createUser);
        UserDTO userDTO = userMapper.toUserDTOFromEntity(savedUser);
        ActivationCode activationCode = activationCodeService.generateCode(userDTO);
        String link = basePath.formatted(activationCode.getActivationLink());
        mailService.sendEmail(userDTO, link);
        return new ApiResponse<>("User successfully registered, please confirm email!", savedUser, true);
    }

    @Transactional(noRollbackFor = GenericRuntimeException.class)
    public Boolean activateUser(String activationCode) {
        ActivationCode activationLink = activationCodeService.findByActivationLink(activationCode);
        if (activationLink.getValidTill().isBefore(LocalDateTime.now())) {
            activationCodeService.delete(activationLink.getId());
            throw new GenericRuntimeException("Activation Code is not active");
        }
        User authUser = userRepository.findById(activationLink.getUserId()).orElseThrow(() -> {
            throw new GenericNotFoundException("User not found");
        });

        authUser.setStatus(UserStatus.ACTIVE);
        userRepository.save(authUser);
        return true;
    }

    public JwtResponseDTO login(LoginDTO dto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accessToken = accessTokenService.generateToken(userDetails);
        String refreshToken = refreshTokenService.generateToken(userDetails);
        return new JwtResponseDTO(accessToken, refreshToken, "Bearer");
    }

    public JwtResponseDTO refreshToken(@NonNull RefreshTokenDTO dto) {
        String token = dto.token();
        if (!accessTokenService.isValid(token)) {
            throw new GenericInvalidTokenException("Refresh Token invalid");
        }
        String subject = refreshTokenService.getSubject(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(subject);
        String accessToken = accessTokenService.generateToken(userDetails);
        return new JwtResponseDTO(accessToken, dto.token(), "Bearer");
    }

    public User getCurrentAuthUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return userRepository.findByEmail(username).orElseThrow(() -> {
            throw new GenericNotFoundException("User not found!");
        });
    }
}
