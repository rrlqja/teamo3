package song.teamo3.domain.studyapplication.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo3.domain.chat.service.ChatRoomService;
import song.teamo3.domain.common.exception.studyapplication.exceptions.StudyApplicationAccessDeniedException;
import song.teamo3.domain.common.exception.studyapplication.exceptions.StudyApplicationNotApproveException;
import song.teamo3.domain.common.exception.studyapplication.exceptions.StudyApplicationNotFoundException;
import song.teamo3.domain.common.exception.studymember.exceptions.DuplicateStudyMemberException;
import song.teamo3.domain.study.dto.CreateStudyApplicationDto;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.studyapplication.dto.StudyApplicationDto;
import song.teamo3.domain.studyapplication.dto.StudyApplicationPageDto;
import song.teamo3.domain.studyapplication.entity.StudyApplication;
import song.teamo3.domain.studyapplication.repository.StudyApplicationJpaRepository;
import song.teamo3.domain.studymember.entity.StudyMemberRole;
import song.teamo3.domain.studymember.service.StudyMemberService;
import song.teamo3.domain.user.entity.User;

import java.util.List;

import static song.teamo3.domain.studyapplication.entity.StudyApplicationStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudyApplicationService {
    private final ChatRoomService chatRoomService;
    private final StudyMemberService studyMemberService;
    private final StudyApplicationJpaRepository studyApplicationRepository;

    @Transactional
    public Long createStudyApplication(User user, Study study, CreateStudyApplicationDto applicationDto) {
        checkDuplicateStudyApplication(user, study);

        StudyApplication studyApplication = applicationDto.toEntity(user, study);

        StudyApplication saveStudyApplication = studyApplicationRepository.save(studyApplication);
        log.info("[Create Study Application] id: {}", saveStudyApplication.getId());

        return saveStudyApplication.getId();
    }

    @Transactional
    public void checkDuplicateStudyApplication(User user, Study study) {
        studyApplicationRepository.findStudyApplicationByUserAndStudy(user, study, PENDING)
                .ifPresent(sa -> {
                    throw new DuplicateStudyMemberException("이미 신청하였습니다.");
                });
    }

    @Transactional
    public List<StudyApplicationPageDto> getPendingStudyApplicationPage(Study study) {
        return studyApplicationRepository.findPendingStudyApplicationsByStudy(study, PENDING).stream()
                .map(StudyApplicationPageDto::new)
                .toList();
    }

    @Transactional
    public StudyApplicationDto getStudyApplication(User user, Long studyApplicationId) {
        StudyApplication studyApplication = studyApplicationRepository.findById(studyApplicationId)
                .orElseThrow(StudyApplicationNotFoundException::new);

        if (!studyApplication.getStudy().getWriter().getId().equals(user.getId())) {
            throw new StudyApplicationAccessDeniedException("잘못된 요청입니다.");
        }

        return new StudyApplicationDto(studyApplication);
    }

    @Transactional
    public Long approve(User user, Long studyApplicationId) {
        StudyApplication studyApplication = studyApplicationRepository.findById(studyApplicationId)
                .orElseThrow(StudyApplicationNotFoundException::new);

        if (!studyApplication.getStatus().equals(PENDING) || !studyApplication.getStudy().getWriter().getId().equals(user.getId())) {
            throw new StudyApplicationNotApproveException("승인할 수 없습니다.");
        }

        studyApplication.approve();

        studyMemberService.createStudyMember(studyApplication.getUser(), studyApplication.getStudy(), StudyMemberRole.MEMBER);

        chatRoomService.addChatRoomUser(studyApplication.getStudy(), studyApplication.getUser());

        return studyApplication.getStudy().getId();
    }
}
