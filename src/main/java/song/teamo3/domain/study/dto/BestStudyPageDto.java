package song.teamo3.domain.study.dto;

import lombok.Data;
import song.teamo3.domain.study.entity.Study;

import java.time.format.DateTimeFormatter;

@Data
public class BestStudyPageDto {
    private Long id;
    private String title;
    private String writerName;
    private String bumpUpDate;
    private Long views;

    public BestStudyPageDto(Study study) {
        this.id = study.getId();
        this.title = study.getTitle();
        this.writerName = study.getWriter().getName();
        this.bumpUpDate = study.getBumpUpDate().format(DateTimeFormatter.ofPattern("yy-MM-dd"));
        this.views = study.getViews();
    }
}
