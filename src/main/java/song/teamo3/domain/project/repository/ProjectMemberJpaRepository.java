package song.teamo3.domain.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.teamo3.domain.project.entity.Project;
import song.teamo3.domain.project.entity.ProjectMember;

import java.util.List;

@Repository
public interface ProjectMemberJpaRepository extends JpaRepository<ProjectMember, Long> {
    @Query("select pm " +
            " from ProjectMember pm " +
            " join fetch pm.user " +
            "where pm.project = :project")
    List<ProjectMember> findProjectMembersByProject(@Param("project") Project project);
}
