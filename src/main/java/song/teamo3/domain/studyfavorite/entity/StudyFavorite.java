package song.teamo3.domain.studyfavorite.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyFavorite {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "study_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Study study;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private boolean deleteFlag = false;

    public static StudyFavorite create(Study study, User user) {
        return new StudyFavorite(study, user);
    }

    private StudyFavorite(Study study, User user) {
        this.study = study;
        this.user = user;
    }

    public void restore() {
        this.deleteFlag = false;
    }

    public void delete() {
        this.deleteFlag = true;
    }
}
