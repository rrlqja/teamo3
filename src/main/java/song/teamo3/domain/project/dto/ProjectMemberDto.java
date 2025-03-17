package song.teamo3.domain.project.dto;

import lombok.Data;
import song.teamo3.domain.project.entity.ProjectMember;

@Data
public class ProjectMemberDto {
    private Long id;
    private String name;

    public ProjectMemberDto(ProjectMember projectMember) {
        this.id = projectMember.getId();
        this.name = projectMember.getUser().getName();
    }
}
