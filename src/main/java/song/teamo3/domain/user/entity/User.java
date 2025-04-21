package song.teamo3.domain.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String name;
    private boolean deleteFlag;

    public void modify(String username, String name) {
        this.username = username;
        this.name = name;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void delete() {
        this.deleteFlag = true;
    }

    public static User create(String username, String password, String name) {
        return new User(username, password, name);
    }

    private User(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.deleteFlag = false;
    }
}
