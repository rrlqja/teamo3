package song.teamo3.domain.studycalendar.dto;

import lombok.Data;
import song.teamo3.domain.studycalendar.entity.ScheduleMemo;

@Data
public class ScheduleMemoDto {
    private Long id;
    private String description;

    private Long studyScheduleId;

    private Long writerId;
    private String writerName;
    private boolean isWriter;

    public ScheduleMemoDto(ScheduleMemo scheduleMemo) {
        this.id = scheduleMemo.getId();
        this.description = scheduleMemo.getDescription();

        this.studyScheduleId = scheduleMemo.getStudySchedule().getId();

        this.writerId = scheduleMemo.getWriter().getId();
        this.writerName = scheduleMemo.getWriter().getName();
        this.isWriter = true;
    }
}
