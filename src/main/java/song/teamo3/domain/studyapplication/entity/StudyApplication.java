package song.teamo3.domain.studyapplication.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import song.teamo3.domain.post.entity.Post;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.user.entity.User;

@Entity
@Getter
@DiscriminatorValue("STUDY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyApplication extends Post {
    @JoinColumn(name = "study_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Study study;

    @Enumerated(EnumType.STRING)
    private StudyApplicationStatus status;

    public static StudyApplication create(User user, Study study, String title, String description) {
        return new StudyApplication(user, study, title, description);
    }

    private StudyApplication(User user, Study study, String title, String description) {
        super(user, title, description);
        this.study = study;
        this.status = StudyApplicationStatus.PENDING;
    }

    public void approve() {
        this.status = StudyApplicationStatus.APPROVED;
    }

    public void reject() {
        this.status = StudyApplicationStatus.REJECTED;
    }
}
