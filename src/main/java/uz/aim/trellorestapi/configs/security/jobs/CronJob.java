package uz.aim.trellorestapi.configs.security.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import uz.aim.trellorestapi.domains.entity.auth.User;
import uz.aim.trellorestapi.domains.enums.auth.UserStatus;
import uz.aim.trellorestapi.repository.auth.UserRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *  @author : Abbosbek Murodov
 *  @since  : 02/04/23 / 11:54
 *  Project : Trello-REST-API / IntelliJ IDEA
*/

@Configuration
@EnableScheduling
public class CronJob {
    @Autowired
    private UserRepository repository;
    private static final Logger LOGGER = LoggerFactory.getLogger(CronJob.class);
    @Scheduled(fixedRate = 2, timeUnit = TimeUnit.MINUTES)
    public void scheduleFixedDelayTask() {
        List<User> authUsers = repository.getAllBlocked().orElse(new ArrayList<>());
        authUsers.stream().filter(authUser -> Duration.between(authUser.getLastLoginTime(), LocalDateTime.now()).getSeconds() >= 120)
                .forEach(authUser -> {
                    authUser.setStatus(UserStatus.ACTIVE);
                    authUser.setLoginTryCount(0);
                    authUser.setLastLoginTime(null);
                });
        repository.saveAll(authUsers);
        LOGGER.info("Blocked users are activated!");
    }
}

