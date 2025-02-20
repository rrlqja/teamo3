package song.teamo3.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo3.domain.common.exception.user.exceptions.DuplicatedUsernameException;
import song.teamo3.domain.user.dto.CreateUserDto;
import song.teamo3.domain.user.entity.User;
import song.teamo3.domain.user.repository.UserJpaRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long createUser(CreateUserDto createUserDto) {
        userRepository.findUserByUsername(createUserDto.getUsername())
                .ifPresent(u -> {
                    throw new DuplicatedUsernameException("이미 존재하는 사용자 이름입니다.");
                });

        User user = createUserDto.toUser(passwordEncoder);

        return userRepository.save(user).getId();
    }
}
