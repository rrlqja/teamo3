package song.teamo3.domain.studycalendar.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class ModifyStudyScheduleDto {
    private String title;
    private String description;
    private String start;
    private String end;

    public LocalDateTime getStart() {
        return LocalDateTime.parse(start, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public LocalDateTime getEnd() {
        return LocalDateTime.parse(end, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
