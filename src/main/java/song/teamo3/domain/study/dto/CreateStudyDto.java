package song.teamo3.domain.study.dto;

import lombok.Data;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.user.entity.User;

@Data
public class CreateStudyDto {
    private String title;
    private String description;
    private String studyName;

    public Study toEntity(User writer) {
        return Study.create(writer, title, description, studyName);
    }
}
