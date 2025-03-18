package song.teamo3.domain.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo3.domain.common.exception.project.exceptions.AlreadyDeletedProjectException;
import song.teamo3.domain.common.exception.project.exceptions.ProjectAccessDeniedException;
import song.teamo3.domain.common.exception.project.exceptions.ProjectModifyNotAllowedException;
import song.teamo3.domain.common.exception.project.exceptions.ProjectNotFoundException;
import song.teamo3.domain.common.exception.study.exceptions.StudyAccessDeniedException;
import song.teamo3.domain.common.exception.study.exceptions.StudyNotFoundException;
import song.teamo3.domain.project.dto.CreateProjectDto;
import song.teamo3.domain.project.dto.CreateProjectMemberListDto;
import song.teamo3.domain.project.dto.ModifyProjectDto;
import song.teamo3.domain.project.dto.ProjectDto;
import song.teamo3.domain.project.dto.ProjectPageDto;
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
    public Page<ProjectPageDto> getProjectPage(Pageable pageable) {
        Page<Project> projectPage = projectRepository.findAll(pageable);

        return projectPage.map(ProjectPageDto::new);
    }

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

        List<Long> studyMemberIdList = studyMemberService.getStudyMemberList(study).stream().map(StudyMemberListDto::getUserId).toList();
        List<User> studyMemberUserList = userJpaRepository.findUsersByIdIn(studyMemberIdList);

        List<ProjectMember> projectMemberList = ProjectMember.create(studyMemberUserList, saveProject);
        List<ProjectMember> saveProjectMemberList = projectMemberRepository.saveAll(projectMemberList);

        log.info("[Create Project] id: {}", saveProject.getId());
        log.info("[Create Project Member] ids: {}", saveProjectMemberList.stream().map(ProjectMember::getId).toList());
        return saveProject.getId();
    }

    @Transactional
    public ProjectDto getProject(Long projectId) {
        Project project = findProjectById(projectId);

        checkDeleted(project);

        List<ProjectMember> projectMemberList = projectMemberRepository.findProjectMembersByProject(project);

        return new ProjectDto(project, projectMemberList);
    }

    @Transactional
    public ProjectDto getProject(User user, Long projectId) {
        Project project = findProjectById(projectId);

        checkDeleted(project);

        List<ProjectMember> projectMemberList = projectMemberRepository.findProjectMembersByProject(project);

        return new ProjectDto(project, projectMemberList, project.getWriter().getId().equals(user.getId()));
    }

    @Transactional
    public ModifyProjectDto getModifyProject(User user, Long projectId) {
        Project project = findProjectById(projectId);

        checkDeleted(project);

        if (!project.getWriter().getId().equals(user.getId())) {
            throw new ProjectModifyNotAllowedException("권한이 없습니다.");
        }

        List<ProjectMember> projectMemberList = projectMemberRepository.findProjectMembersByProject(project);

        return new ModifyProjectDto(project, projectMemberList);
    }

    @Transactional
    public Long modifyProject(User user, Long projectId, ModifyProjectDto modifyProjectDto) {
        Project project = findProjectById(projectId);

        checkDeleted(project);

        if (!project.getWriter().getId().equals(user.getId())) {
            throw new ProjectModifyNotAllowedException("권한이 없습니다.");
        }

        project.modify(modifyProjectDto.getTitle(), modifyProjectDto.getDescription(), modifyProjectDto.getImgList(), modifyProjectDto.getUrl());
        Project modifiedProject = projectRepository.save(project);

        return modifiedProject.getId();
    }

    @Transactional
    public void deleteProject(User user, Long projectId) {
        Project project = findProjectById(projectId);

        checkDeleted(project);

        if (!project.getWriter().getId().equals(user.getId())) {
            throw new ProjectAccessDeniedException("권한이 없습니다.");
        }

        project.delete();
    }

    private Project findProjectById(Long projectId) {
        return projectRepository.findProjectById(projectId)
                .orElseThrow(ProjectNotFoundException::new);
    }

    private void checkDeleted(Project project) {
        if (project.isDeleteFlag()) {
            throw new AlreadyDeletedProjectException("삭제된 프로젝트입니다.");
        }
    }
}
