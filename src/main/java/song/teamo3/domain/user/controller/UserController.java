package song.teamo3.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import song.teamo3.domain.user.dto.CreateUserDto;
import song.teamo3.domain.user.dto.LoginDto;
import song.teamo3.domain.user.service.UserService;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public String postCreateUser(@Valid CreateUserDto userDto) {
        Long userId = userService.createUser(userDto);

        return "redirect:/user/login";
    }

    @GetMapping("/login")
    public String getLogin(@ModelAttribute("loginDto") LoginDto loginDto) {

        return "user/login";
    }

    @GetMapping("/signup")
    public String getSignup(@ModelAttribute(name = "user") CreateUserDto userDto) {

        return "user/signup";
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/signup/validateUsername")
    public void getValidateUsername(@RequestParam(name = "username") String username) {
        userService.validateUsername(username);
    }
}
