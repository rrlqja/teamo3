package song.teamo3.domain.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo3.domain.common.exception.project.exceptions.ProjectNotFoundException;
import song.teamo3.domain.common.exception.study.exceptions.StudyAccessDeniedException;
import song.teamo3.domain.common.exception.study.exceptions.StudyNotFoundException;
import song.teamo3.domain.project.dto.CreateProjectDto;
import song.teamo3.domain.project.dto.CreateProjectMemberListDto;
import song.teamo3.domain.project.dto.ProjectDto;
import song.teamo3.domain.project.entity.Project;
import song.teamo3.domain.project.entity.ProjectMember;
import song.teamo3.domain.project.repository.ProjectJpaRepository;
import song.teamo3.domain.project.repository.ProjectMemberJpaRepository;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.study.repository.StudyJpaRepository;
import song.teamo3.domain.studymember.dto.StudyMemberListDto;
import song.teamo3.domain.studymember.service.StudyMemberService;
import song.teamo3.domain.user.entity.User;
import song.teamo3.domain.user.repository.UserJpaRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {
    private final StudyMemberService studyMemberService;
    private final ProjectJpaRepository projectRepository;
    private final ProjectMemberJpaRepository projectMemberRepository;
    private final StudyJpaRepository studyRepository;
    private final UserJpaRepository userJpaRepository;


    @Transactional
    public CreateProjectDto getCreateProject(User user, Long studyId) {
        Study study = studyRepository.findStudyById(studyId)
                .orElseThrow(StudyNotFoundException::new);

        if (!study.getWriter().getId().equals(user.getId())) {
            throw new StudyAccessDeniedException("권한이 없습니다.");
        }

        List<CreateProjectMemberListDto> studyMemberList = studyMemberService.getStudyMemberList(study)
                .stream().map(CreateProjectMemberListDto::new)
                .toList();
        return new CreateProjectDto(study, studyMemberList);
    }

    @Transactional
    public Long create(User user, CreateProjectDto projectDto) {
        Study study = studyRepository.findStudyById(projectDto.getStudyId())
                .orElseThrow(StudyNotFoundException::new);

        Project project = projectDto.toEntity(user, study);

        Project saveProject = projectRepository.save(project);

        List<User> studyMemberList = userJpaRepository.findUsersByIdIn(
                projectDto.getProjectMemberList().stream().map(CreateProjectMemberListDto::getMemberId).toList());

        List<ProjectMember> projectMemberList = ProjectMember.create(studyMemberList, saveProject);

        List<ProjectMember> saveProjectMemberList = projectMemberRepository.saveAll(projectMemberList);

        log.info("[Create Project] id: {}", saveProject.getId());
        log.info("[Create Project Member] ids: {}", saveProjectMemberList.stream().map(ProjectMember::getId).toList());
        return saveProject.getId();
    }

    @Transactional
    public ProjectDto getProject(Long projectId) {
        Project project = projectRepository.findProjectById(projectId)
                .orElseThrow(ProjectNotFoundException::new);

        List<ProjectMember> projectMemberList = projectMemberRepository.findProjectMembersByProject(project);

        return new ProjectDto(project, projectMemberList);
    }
}
