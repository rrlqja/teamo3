package song.teamo3.domain.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import song.teamo3.domain.chat.dto.ChatRoomListDto;
import song.teamo3.domain.chat.service.ChatRoomService;
import song.teamo3.domain.user.dto.ChangePasswordDto;
import song.teamo3.domain.user.dto.CreateUserDto;
import song.teamo3.domain.user.dto.LoginDto;
import song.teamo3.domain.user.dto.ModifyUserDto;
import song.teamo3.domain.user.dto.UserDto;
import song.teamo3.domain.user.entity.User;
import song.teamo3.domain.user.service.UserService;
import song.teamo3.security.authentication.userdetails.UserDetailsImpl;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ChatRoomService chatRoomService;
    private final UserDetailsService userDetailsService;

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

    @GetMapping("/userInfo")
    public String getUser(@AuthenticationPrincipal UserDetailsImpl userDetails,
                          Model model) {
        getModelData(userDetails.getUser(), model);

        return "user/userInfo";
    }

    @GetMapping("/userInfo/modify")
    public String getModifyUser(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                Model model) {
        getModelData(userDetails.getUser(), model);

        return "user/modifyuser";
    }

    @PostMapping("/userInfo/modify")
    public String postModifyUser(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                 ModifyUserDto modifyUserDto,
                                 Model model) {
        userService.modify(userDetails.getUser(), modifyUserDto);

        setSecurityContextHolder(userDetails);

        getModelData(userDetails.getUser(), model);

        return "redirect:/user/userInfo";
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/userInfo/validateUsername")
    public void getValidateUsername(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @RequestParam(name = "username") String username) {
        userService.validateUsername(userDetails.getUser(), username);
    }

    @GetMapping("/userInfo/changePassword")
    public String getChangePassword(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @ModelAttribute(name = "changePasswordDto") ChangePasswordDto changePasswordDto,
                                    Model model) {
        getModelData(userDetails.getUser(), model);

        return "user/changepassword";
    }

    @PostMapping("/userInfo/changePassword")
    public String postChangePassword(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                     @ModelAttribute(name = "changePasswordDto") ChangePasswordDto changePasswordDto,
                                     Model model) {
        getModelData(userDetails.getUser(), model);

        userService.changePassword(userDetails.getUser(), changePasswordDto);

        return "redirect:/user/userInfo";
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/delete")
    public void postDeleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails,
                               HttpServletRequest request) {
        userService.delete(userDetails.getUser());

        SecurityContextHolder.clearContext();
        request.getSession().invalidate();
    }

    private void getModelData(User user, Model model) {
        model.addAttribute("noticeList", Page.empty());
        Page<ChatRoomListDto> chatRoomList = chatRoomService.getChatRoomList(user);
        model.addAttribute("chatRoomList", chatRoomList);

        model.addAttribute("user", new UserDto(user));
    }

    private void setSecurityContextHolder(UserDetailsImpl userDetails) {
        UserDetails userImpl = userDetailsService.loadUserByUsername(userDetails.getUsername());
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userImpl, "", userImpl.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
