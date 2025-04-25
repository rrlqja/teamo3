package song.teamo3.domain.studyapplication.dto;

import lombok.Data;
import song.teamo3.domain.studyapplication.entity.StudyApplication;

import java.time.format.DateTimeFormatter;

@Data
public class StudyApplicationPageDto {
    private Long id;
    private String title;
    private String description;
    private String status;

    private String writerName;
    private String createDate;

    public StudyApplicationPageDto(StudyApplication studyApplication) {
        this.id = studyApplication.getId();
        this.title = studyApplication.getTitle();
        this.description = studyApplication.getContent();
        this.status = studyApplication.getStatus().toString();

        this.writerName = studyApplication.getWriter().getName();
        this.createDate=studyApplication.getCreateDate().format(DateTimeFormatter.ofPattern("yy.MM.dd HH:mm"));
    }
}
