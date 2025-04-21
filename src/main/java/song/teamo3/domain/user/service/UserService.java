package song.teamo3.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo3.domain.common.exception.user.exceptions.PasswordChangeException;
import song.teamo3.domain.common.exception.user.exceptions.DuplicatedUsernameException;
import song.teamo3.domain.user.dto.ChangePasswordDto;
import song.teamo3.domain.user.dto.CreateUserDto;
import song.teamo3.domain.user.dto.ModifyUserDto;
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
        validateUsername(createUserDto.getUsername());

        User user = createUserDto.toUser(passwordEncoder);

        User createUser = userRepository.save(user);

        log.info("User created: {}", createUser.getId());
        return createUser.getId();
    }

    @Transactional
    public void validateUsername(String username) {
        userRepository.findUserByUsername(username)
                .ifPresent(user-> {
                    throw new DuplicatedUsernameException("이미 존재하는 아이디입니다.");
                });
    }

    @Transactional
    public void validateUsername(User user, String username) {
        userRepository.findUserByUsername(username)
                .ifPresent(u -> {
                    if (!u.getId().equals(user.getId())) {
                        throw new DuplicatedUsernameException("이미 존재하는 아이디입니다.");
                    }
                });
    }

    @Transactional
    public void modify(User user, ModifyUserDto modifyUserDto) {
        validateUsername(user, modifyUserDto.getUsername());

        user.modify(modifyUserDto.getUsername(), modifyUserDto.getName());

        userRepository.save(user);
    }

    @Transactional
    public void changePassword(User user, ChangePasswordDto changePasswordDto) {
        if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())) {
            throw new PasswordChangeException("현재 비밀번호를 다시 입력해주세요");
        }

        if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmPassword())) {
            throw new PasswordChangeException("새 비밀번호가 일치하지 않습니다.");
        }

        if (passwordEncoder.matches(changePasswordDto.getNewPassword(), user.getPassword())) {
            throw new PasswordChangeException("동일한 비밀번호로 변경할 수 없습니다.");
        }

        user.changePassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void delete(User user) {
        user.delete();
        userRepository.save(user);
    }
}
