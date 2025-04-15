package song.teamo3.domain.studycalendar.dto;

import lombok.Data;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.studycalendar.entity.StudySchedule;
import song.teamo3.domain.user.entity.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class CreateStudyScheduleDto {
    private String name;
    private String description;
    private String startDate;
    private String endDate;

    private Long studyId;

    public StudySchedule toEntity(User user, Study study) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime start = LocalDateTime.parse(startDate, formatter);
        LocalDateTime end = LocalDateTime.parse(endDate, formatter);

        return StudySchedule.create(study, user, name, description, start, end);
    }
}
