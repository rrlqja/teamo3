package song.teamo3.domain.study.dto;

import lombok.Data;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.user.entity.User;

@Data
public class CreateStudyDto {
    private String title;
    private String description;

    public Study toEntity(User writer) {
        return Study.create(writer, title, description);
    }
}
