package song.teamo3.domain.study.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import song.teamo3.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Study {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "writer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User writer;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private StudyStatus status;

    public static Study create(User writer, String title, String description) {
        return new Study(writer, title, description, StudyStatus.RECRUITING);
    }

    private Study(User writer, String title, String description, StudyStatus status) {
        this.writer = writer;
        this.title = title;
        this.description = description;
        this.status = status;
    }
}
