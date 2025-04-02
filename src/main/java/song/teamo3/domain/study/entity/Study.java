package song.teamo3.domain.study.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import song.teamo3.domain.post.entity.Post;
import song.teamo3.domain.favorite.entity.Favorite;
import song.teamo3.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DiscriminatorValue("STUDY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Study extends Post {
    @Enumerated(EnumType.STRING)
    private StudyStatus status;

    @CreatedDate
    private LocalDateTime bumpUpDate;

    public StudyStatus changeStatus() {
        this.status = this.status.changeStatus();

        return this.status;
    }

    public void bumpUp() {
        bumpUpDate = LocalDateTime.now();
    }

    public static Study create(User writer, String title, String description) {
        return new Study(writer, title, description, StudyStatus.RECRUITING);
    }

    private Study(User writer, String title, String description, StudyStatus status) {
        super(writer, title, description);
        this.status = status;
    }
}
