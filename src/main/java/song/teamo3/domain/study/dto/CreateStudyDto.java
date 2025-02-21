package song.teamo3.domain.study.dto;

import lombok.Data;
import song.teamo3.domain.study.entity.Study;

@Data
public class CreateStudyDto {
    private String title;
    private String description;

    public Study toEntity() {
        return Study.create(title, description);
    }
}
