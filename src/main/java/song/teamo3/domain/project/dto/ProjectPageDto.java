package song.teamo3.domain.project.dto;

import lombok.Data;
import song.teamo3.domain.project.entity.Project;

@Data
public class ProjectPageDto {
    private Long id;
    private String title;
    private String description;
    private String writerName;
    private String imgUrl;

    public ProjectPageDto(Project project) {
        this.id = project.getId();
        this.title = project.getTitle();
        this.description = project.getDescription();
        this.writerName = project.getWriter().getName();
        this.imgUrl = !project.getImgList().isEmpty() ? project.getImgList().get(0) : null;
    }
}
