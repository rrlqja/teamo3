package song.teamo3.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import song.teamo3.domain.user.entity.User;
import song.teamo3.domain.user.repository.UserJpaRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitConfig {
    private final InitService initService;

    @PostConstruct
    public void setInit() {
        initService.init();
    }

    @Component
    @RequiredArgsConstructor
    private static class InitService {
        private final PasswordEncoder passwordEncoder;
        private final UserJpaRepository userRepository;

        public void init() {
            User user1 = userRepository.save(User.create("a", passwordEncoder.encode("a"), "a"));
        }
    }
}
