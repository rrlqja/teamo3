package song.teamo3.domain.study.dto;

import lombok.Data;
import song.teamo3.domain.study.entity.Study;

@Data
public class StudyPageDto {
    private Long id;
    private String title;
    private String writerName;

    public StudyPageDto(Study study) {
        this.id = study.getId();
        this.title = study.getTitle();
        this.writerName = study.getWriter().getName();
    }
}
