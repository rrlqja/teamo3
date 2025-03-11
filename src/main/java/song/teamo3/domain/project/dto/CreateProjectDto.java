package song.teamo3.domain.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import song.teamo3.domain.project.entity.Project;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjectDto {
    private Long studyId;
    private String title;
    private String description;
    private String url;
    private List<CreateProjectMemberListDto> projectMemberList = new ArrayList<>();
    private List<String> imgList = new ArrayList<>();

    public CreateProjectDto(Study study, List<CreateProjectMemberListDto> studyMemberList) {
        this.studyId = study.getId();
        this.projectMemberList = studyMemberList;
    }

    public Project toEntity(User user, Study study) {
        return Project.create(user, study, title, description, imgList, url);
    }
}
