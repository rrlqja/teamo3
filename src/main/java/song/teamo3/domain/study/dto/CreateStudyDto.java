package song.teamo3.domain.study.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.user.entity.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudyDto {
    private String title;
    private String description;
    private String studyName;

    public Study toEntity(User writer) {
        return Study.create(writer, title, description, studyName);
    }
}
