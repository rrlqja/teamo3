package song.teamo3.domain.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import song.teamo3.domain.project.entity.Project;
import song.teamo3.domain.project.entity.ProjectMember;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyProjectDto {
    private Long id;
    private String title;
    private String description;
    private String url;
    private List<String> imgList = new ArrayList<>();
    private List<ProjectMemberDto> projectMemberList = new ArrayList<>();

    public ModifyProjectDto(Project project, List<ProjectMember> projectMemberList) {
        this.id = project.getId();
        this.title = project.getTitle();
        this.description = project.getContent();
        this.url = project.getUrl();
        this.imgList = project.getImgList();
        this.projectMemberList = projectMemberList.stream().map(ProjectMemberDto::new).toList();
    }
}
