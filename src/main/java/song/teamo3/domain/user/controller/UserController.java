package song.teamo3.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import song.teamo3.domain.user.dto.CreateUserDto;
import song.teamo3.domain.user.dto.LoginDto;
import song.teamo3.domain.user.service.UserService;

@Slf4j
//@RestController
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ResponseBody
    @PostMapping("/create")
    public ResponseEntity<Void> postCreateUser(@Valid @RequestBody CreateUserDto userDto) {
        Long userId = userService.createUser(userDto);
        log.info("User created: {}", userId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/login")
    public String getLogin(@ModelAttribute("loginDto") LoginDto loginDto) {

        return "user/login";
    }
}
