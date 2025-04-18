package song.teamo3.domain.study.dto;

import lombok.Data;
import song.teamo3.domain.study.entity.Study;

import java.time.format.DateTimeFormatter;

@Data
public class StudyPageDto {
    private Long id;
    private String title;
    private String studyName;
    private String writerName;
    private String bumpUpDate;
    private Long views;
    private String status;

    public StudyPageDto(Study study) {
        this.id = study.getId();
        this.title = study.getTitle();
        this.studyName = study.getStudyName();
        this.writerName = study.getWriter().getName();
        this.bumpUpDate = study.getBumpUpDate().format(DateTimeFormatter.ofPattern("yy-MM-dd"));
        this.views = study.getViews();
        this.status = study.getStatus().name();
    }
}
