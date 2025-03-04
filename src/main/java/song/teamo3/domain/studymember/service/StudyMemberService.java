package song.teamo3.domain.studymember.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo3.domain.common.exception.studymember.exceptions.DuplicateStudyMemberException;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.studymember.dto.StudyMemberPageDto;
import song.teamo3.domain.studymember.entity.StudyMember;
import song.teamo3.domain.studymember.entity.StudyMemberRole;
import song.teamo3.domain.studymember.repository.StudyMemberJpaRepository;
import song.teamo3.domain.user.entity.User;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudyMemberService {
    private final StudyMemberJpaRepository studyMemberRepository;

    @Transactional
    public Long createStudyMember(User user, Study study, StudyMemberRole role) {
        findStudyMemberByUserAndStudy(user, study)
                .ifPresent(studyMember -> {
                    throw new DuplicateStudyMemberException("이미 가입된 사용자입니다.");
                });

        StudyMember createStudyMember = studyMemberRepository.save(StudyMember.create(user, study, role));

        log.info("[Create StudyMember] id: {}", createStudyMember.getId());
        return createStudyMember.getId();
    }

    @Transactional
    public void checkDuplicateStudyMember(User user, Study study) {
        findStudyMemberByUserAndStudy(user, study)
                .ifPresent(studyMember -> {
                    throw new DuplicateStudyMemberException("이미 가입한 스터디입니다.");
                });
    }

    @Transactional
    public Page<StudyMemberPageDto> getStudyMemberPage(User user, Pageable pageable) {
        return studyMemberRepository.findStudyMembersByUser(user, pageable)
                .map(StudyMemberPageDto::new);
    }

    private Optional<StudyMember> findStudyMemberByUserAndStudy(User user, Study study) {
        return studyMemberRepository.findStudyMemberByUserAndStudy(user, study);
    }
}
