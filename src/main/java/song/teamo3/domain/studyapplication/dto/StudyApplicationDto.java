package song.teamo3.domain.studyapplication.dto;

import lombok.Data;
import song.teamo3.domain.studyapplication.entity.StudyApplication;

import java.time.format.DateTimeFormatter;

@Data
public class StudyApplicationDto {
    private Long id;
    private String title;
    private String description;
    private String createDate;

    private String username;

    public StudyApplicationDto(StudyApplication studyApplication) {
        this.id = studyApplication.getId();
        this.title = studyApplication.getTitle();
        this.description = studyApplication.getContent();
        this.createDate = studyApplication.getCreateDate().format(DateTimeFormatter.ofPattern("yy.MM.dd HH:mm"));

        this.username = studyApplication.getWriter().getName();
    }
}
