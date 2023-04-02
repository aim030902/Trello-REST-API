package uz.aim.trellorestapi.services.auth;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.aim.trellorestapi.domains.entity.auth.ActivationCode;
import uz.aim.trellorestapi.dtos.user.UserDTO;
import uz.aim.trellorestapi.exception.GenericNotFoundException;
import uz.aim.trellorestapi.repository.auth.ActivationCodeRepository;
import uz.aim.trellorestapi.utils.base.BaseUtils;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 12:10
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Service
public class ActivationCodeService {
    @Autowired
    private BaseUtils baseUtils;
    @Autowired
    private ActivationCodeRepository repository;

    @Value("${activation.link.expiry.in.minutes}")
    private long activationLinkValidTillInMinutes;

    public ActivationCode generateCode(@NonNull UserDTO authUserDTO) {
        String codeForEncoding = "" + UUID.randomUUID() + System.currentTimeMillis();
        String encodedActivationCode = baseUtils.encode(codeForEncoding);
        ActivationCode activationCode = ActivationCode.builder()
                .activationLink(encodedActivationCode)
                .userId(authUserDTO.getId())
                .validTill(LocalDateTime.now().plusMinutes(activationLinkValidTillInMinutes))
                .build();
        return repository.save(activationCode);
    }

    public ActivationCode findByActivationLink(@NonNull String activationLink) {
        return repository.findByActivationLink(activationLink).orElseThrow(() ->
        {
            throw new GenericNotFoundException("Activation Link Not Found");
        });
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}

