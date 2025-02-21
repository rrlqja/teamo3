package song.teamo3.domain.study.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Study {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private StudyStatus status;

    public static Study create(String title, String description) {
        return new Study(title, description, StudyStatus.RECRUITING);
    }

    private Study(String title, String description, StudyStatus status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }
}
