package song.teamo3.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import song.teamo3.domain.user.dto.CreateUserDto;
import song.teamo3.domain.user.service.UserService;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Void> postCreateUser(@Valid @RequestBody CreateUserDto userDto) {
        Long userId = userService.createUser(userDto);
        log.info("User created: {}", userId);

        return ResponseEntity.ok().build();
    }
}
