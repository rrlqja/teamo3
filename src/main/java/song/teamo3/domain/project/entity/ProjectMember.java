package song.teamo3.domain.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import song.teamo3.domain.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectMember {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "project_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    public static List<ProjectMember> create(List<User> userList, Project project) {
        List<ProjectMember> projectMemberList = new ArrayList<>();

        for (User user : userList) {
            ProjectMember projectMember = new ProjectMember(user, project);
            projectMemberList.add(projectMember);
        }

        return projectMemberList;
    }

    private ProjectMember(User user, Project project) {
        this.user = user;
        this.project = project;
    }
}
