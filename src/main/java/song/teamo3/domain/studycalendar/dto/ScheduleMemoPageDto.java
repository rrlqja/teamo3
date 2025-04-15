package song.teamo3.domain.studycalendar.dto;

import lombok.Data;
import song.teamo3.domain.studycalendar.entity.ScheduleMemo;
import song.teamo3.domain.user.entity.User;

@Data
public class ScheduleMemoPageDto {
    private Long id;
    private String description;
    private boolean isWriter;

    public ScheduleMemoPageDto(ScheduleMemo scheduleMemo, User user) {
        this.id = scheduleMemo.getId();
        this.description = scheduleMemo.getDescription();
        this.isWriter = scheduleMemo.getWriter().getId().equals(user.getId());
    }
}
