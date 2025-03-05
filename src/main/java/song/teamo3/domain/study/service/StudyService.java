package song.teamo3.domain.study.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo3.domain.common.exception.study.exceptions.ClosedStudyException;
import song.teamo3.domain.common.exception.study.exceptions.StudyAccessDeniedException;
import song.teamo3.domain.common.exception.study.exceptions.StudyEditNotAllowedException;
import song.teamo3.domain.common.exception.study.exceptions.StudyNotFoundException;
import song.teamo3.domain.study.dto.CreateStudyApplicationDto;
import song.teamo3.domain.study.dto.CreateStudyDto;
import song.teamo3.domain.study.dto.EditStudyDto;
import song.teamo3.domain.study.dto.StudyDto;
import song.teamo3.domain.study.dto.StudyPageDto;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.study.entity.StudyStatus;
import song.teamo3.domain.study.repository.StudyJpaRepository;
import song.teamo3.domain.studyapplication.dto.StudyApplicationPageDto;
import song.teamo3.domain.studyapplication.service.StudyApplicationService;
import song.teamo3.domain.studymember.dto.StudyMemberListDto;
import song.teamo3.domain.studymember.entity.StudyMemberRole;
import song.teamo3.domain.studymember.service.StudyMemberService;
import song.teamo3.domain.user.entity.User;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudyService {
    private final StudyMemberService studyMemberService;
    private final StudyApplicationService studyApplicationService;
    private final StudyJpaRepository studyRepository;

    @Transactional
    public Page<StudyPageDto> getStudyPage(Pageable pageable) {
        return studyRepository.findStudyPage(pageable)
                .map(StudyPageDto::new);
    }

    @Transactional
    public Long createStudy(User user, CreateStudyDto createStudyDto) {
        Study study = createStudyDto.toEntity(user);

        Study createStudy = studyRepository.save(study);

        studyMemberService.createStudyMember(user, study, StudyMemberRole.ADMIN);

        log.info("[Create Study] id: {}", createStudy.getId());
        return createStudy.getId();
    }

    @Transactional
    public StudyDto getStudy(Long studyId) {
        Study study = findStudyById(studyId);

        List<StudyMemberListDto> studyMemberList = studyMemberService.getStudyMemberList(study);

        incrementViews(study);

        log.info("[Get Study] id: {}", study.getId());
        return new StudyDto(study, studyMemberList);
    }

    @Transactional
    public StudyDto getStudy(User user, Long studyId) {
        Study study = findStudyById(studyId);

        List<StudyMemberListDto> studyMemberList = studyMemberService.getStudyMemberList(study);

        incrementViews(study);

        boolean isMember = studyMemberService.isMember(user, study);

        log.info("[Get Study] id: {}", study.getId());
        return new StudyDto(study, user, studyMemberList, isMember);
    }

    @Transactional
    public Long editStudy(User user, Long studyId, EditStudyDto editStudyDto) {
        Study study = findStudyById(studyId);

        if (study.getWriter().getId() != user.getId()) {
            throw new StudyEditNotAllowedException("수정할 수 없습니다.");
        }

        study.editPost(editStudyDto.getTitle(), editStudyDto.getDescription());
        Study editStudy = studyRepository.save(study);

        log.info("[Edit Study] id: {}", editStudy.getId());
        return editStudy.getId();
    }

    @Transactional
    public void deleteStudy(User user, Long studyId) {
        Study study = findStudyById(studyId);

    }

    @Transactional
    public Long applyStudy(User user, Long studyId, CreateStudyApplicationDto applicationDto) {
        Study study = findStudyById(studyId);

        if (study.getStatus() != StudyStatus.RECRUITING) {
            throw new ClosedStudyException("모집중이지 않은 스터디 입니다.");
        }

        studyMemberService.checkDuplicateStudyMember(user, study);

        studyApplicationService.createStudyApplication(user, study, applicationDto);

        return study.getId();
    }

    @Transactional
    public Long exitStudy(User user, Long studyId) {
        Study study = findStudyById(studyId);

        studyMemberService.exitStudyMember(user, study);

        return study.getId();
    }

    @Transactional
    public Page<StudyApplicationPageDto> getStudyApplicationPage(User user, Long studyId, Pageable pageable) {
        Study study = findStudyById(studyId);

        if (study.getWriter().getId() != user.getId()) {
            throw new StudyAccessDeniedException("권한이 없습니다.");
        }

        return studyApplicationService.getPendingStudyApplicationPage(study, pageable);
    }

    @Transactional
    public Long changeStatus(User user, Long studyId) {
        Study study = findStudyById(studyId);

        if (study.getWriter().getId() != user.getId()) {
            throw new StudyAccessDeniedException("권한이 없습니다.");
        }

        StudyStatus studyStatus = study.changeStatus();
        log.info("[Change Study Status] id: {}, status: {}", study.getId(), studyStatus.name());
        return study.getId();
    }

    private Study findStudyById(Long studyId) {
        return studyRepository.findStudyById(studyId)
                .orElseThrow(StudyNotFoundException::new);
    }

    private void incrementViews(Study study) {
        study.incrementViews();
    }
}
