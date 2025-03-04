package song.teamo3.domain.studyapplication.entity;

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
import song.teamo3.domain.common.entity.PostEntity;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyApplication extends PostEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "study_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Study study;

    @Enumerated(EnumType.STRING)
    private StudyApplicationStatus status;

    public static StudyApplication create(User user, Study study, String title, String description) {
        return new StudyApplication(user, study, title, description);
    }

    private StudyApplication(User user, Study study, String title, String description) {
        super(title, description);
        this.user = user;
        this.study = study;
        this.status = StudyApplicationStatus.PENDING;
    }
}
