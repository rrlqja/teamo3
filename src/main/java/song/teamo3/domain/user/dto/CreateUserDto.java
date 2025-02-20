package song.teamo3.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import song.teamo3.domain.user.entity.User;

@Data
public class CreateUserDto {
//    @Size(min = 8, max = 20, message = "8 ~ 20자 사이여야 합니다.")
    @NotBlank(message = "아이디는 필수값입니다.")
    private String username;
//    @Size(min = 8, max = 20, message = "8 ~ 20자 사이여야 합니다.")
    @NotBlank(message = "비밀번호는 필수값입니다.")
    private String password;

//    @Size(max = 10, message = "10자 이하여야 합니다.")
    @NotBlank(message = "이름은 필수값입니다.")
    private String name;

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.create(username, passwordEncoder.encode(password), name);
    }
}
