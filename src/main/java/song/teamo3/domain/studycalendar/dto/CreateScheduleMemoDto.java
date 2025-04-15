package song.teamo3.domain.studycalendar.dto;

import lombok.Data;
import song.teamo3.domain.studycalendar.entity.ScheduleMemo;
import song.teamo3.domain.studycalendar.entity.StudySchedule;
import song.teamo3.domain.user.entity.User;

@Data
public class CreateScheduleMemoDto {
    private String description;

    public ScheduleMemo toEntity(User user, StudySchedule studySchedule) {
        return ScheduleMemo.create(user, studySchedule, description);
    }
}
