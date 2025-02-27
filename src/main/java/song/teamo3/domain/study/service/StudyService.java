package song.teamo3.domain.study.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo3.domain.common.exception.study.exceptions.StudyNotFoundException;
import song.teamo3.domain.study.dto.CreateStudyDto;
import song.teamo3.domain.study.dto.StudyDto;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.study.repository.StudyJpaRepository;
import song.teamo3.domain.studymember.entity.StudyMember;
import song.teamo3.domain.studymember.entity.StudyMemberRole;
import song.teamo3.domain.studymember.service.StudyMemberService;
import song.teamo3.domain.user.entity.User;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudyService {
    private final StudyMemberService studyMemberService;
    private final StudyJpaRepository studyRepository;

    @Transactional
    public Page<Study> getStudyPage(Pageable pageable) {
        return studyRepository.findStudyPage(pageable);
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
        Study study = studyRepository.findStudyById(studyId)
                .orElseThrow(StudyNotFoundException::new);

        return new StudyDto(study);
    }
}
