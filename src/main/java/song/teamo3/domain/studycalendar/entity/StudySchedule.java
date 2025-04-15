package song.teamo3.domain.studycalendar.entity;

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

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudySchedule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "study_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Study study;

    @JoinColumn(name = "writer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User writer;

    private String title;
    private String description;
    private boolean deleteFlag;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public void modify(String title, String description, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.description = description;
        this.startTime = startDate;
        this.endTime = endDate;
    }

    public void delete() {
        this.deleteFlag = true;
    }

    public static StudySchedule create(Study study, User user, String name, String description, LocalDateTime startTime, LocalDateTime endTime) {
        return new StudySchedule(study, user, name, description, startTime, endTime);
    }

    private StudySchedule(Study study, User user, String title, String description, LocalDateTime startTime, LocalDateTime endTime) {
        this.study = study;
        this.writer = user;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.deleteFlag = false;
    }
}
