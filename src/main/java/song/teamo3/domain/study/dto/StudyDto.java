package song.teamo3.domain.study.dto;

import lombok.Data;
import song.teamo3.domain.study.entity.Study;

@Data
public class StudyDto {
    private Long id;
    private String title;
    private String description;

    private String writerName;

    public StudyDto(Study study) {
        this.id = study.getId();
        this.title = study.getTitle();
        this.description = study.getDescription();

        this.writerName = study.getWriter().getUsername();
    }
}
