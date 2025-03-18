package song.teamo3.domain.project.dto;

import lombok.Data;
import song.teamo3.domain.project.entity.Project;
import song.teamo3.domain.project.entity.ProjectMember;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectDto {
    private Long id;
    private String title;
    private String description;
    private String url;
    private List<String> imgList = new ArrayList<>();
    private List<String> memberNameList = new ArrayList<>();

    private boolean isWriter = false;

    public ProjectDto(Project project, List<ProjectMember> projectMemberList) {
        this.id = project.getId();
        this.title = project.getTitle();
        this.description = project.getDescription();
        this.url = project.getUrl();
        this.imgList = project.getImgList();
        this.memberNameList = projectMemberList.stream().map(pm -> pm.getUser().getName()).toList();
    }

    public ProjectDto(Project project, List<ProjectMember> projectMemberList, boolean isWriter) {
        this.id = project.getId();
        this.title = project.getTitle();
        this.description = project.getDescription();
        this.url = project.getUrl();
        this.imgList = project.getImgList();
        this.memberNameList = projectMemberList.stream().map(pm -> pm.getUser().getName()).toList();
        this.isWriter = isWriter;
    }
}
