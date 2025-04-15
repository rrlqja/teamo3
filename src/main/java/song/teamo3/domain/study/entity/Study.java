package song.teamo3.domain.study.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import song.teamo3.domain.post.entity.Post;
import song.teamo3.domain.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Getter
@DiscriminatorValue("STUDY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Study extends Post {
    private String studyName;
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

    public static Study create(User writer, String title, String description, String studyName) {
        return new Study(writer, title, description, studyName, StudyStatus.RECRUITING);
    }

    private Study(User writer, String title, String description, String studyName, StudyStatus status) {
        super(writer, title, description);
        this.studyName = studyName;
        this.status = status;
    }
}
