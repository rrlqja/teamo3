package song.teamo3.domain.studycalendar.dto;

import lombok.Data;
import song.teamo3.domain.studycalendar.entity.StudySchedule;
import song.teamo3.domain.user.entity.User;

import java.time.format.DateTimeFormatter;

@Data
public class StudyScheduleDto {
    private Long id;
    private String title;
    private String description;
    private String start;
    private String end;

    private boolean isAllDay = true;

    private Long studyId;
    private String studyName;

    private Long writerId;
    private String writerName;
    private boolean isWriter = false;

    public StudyScheduleDto(StudySchedule studySchedule, User user) {
        this.id = studySchedule.getId();
        this.title = studySchedule.getTitle();
        this.description = studySchedule.getDescription();
        this.start = studySchedule.getStartTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.end = studySchedule.getEndTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        this.studyId = studySchedule.getStudy().getId();
        this.studyName = studySchedule.getStudy().getStudyName();

        this.writerName = studySchedule.getWriter().getName();
        this.isWriter = studySchedule.getWriter().getId().equals(user.getId());
    }
}
