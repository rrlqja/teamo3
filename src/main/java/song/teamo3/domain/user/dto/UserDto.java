package song.teamo3.domain.user.dto;

import lombok.Data;
import song.teamo3.domain.user.entity.User;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String name;
    private String email;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
    }
}
