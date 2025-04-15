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
import song.teamo3.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleMemo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "writer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User writer;

    @JoinColumn(name = "schedule_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private StudySchedule studySchedule;

    private String description;
    private boolean deleteFlag;

    public void modify(String description) {
        this.description = description;
    }

    public void delete() {
        this.deleteFlag = true;
    }

    public static ScheduleMemo create(User user, StudySchedule studySchedule, String description) {
        return new ScheduleMemo(user, studySchedule, description);
    }

    private ScheduleMemo(User writer, StudySchedule studySchedule, String description) {
        this.writer = writer;
        this.studySchedule = studySchedule;
        this.description = description;
        this.deleteFlag = false;
    }
}
