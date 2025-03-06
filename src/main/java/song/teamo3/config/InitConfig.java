package song.teamo3.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import song.teamo3.domain.comment.entity.Comment;
import song.teamo3.domain.comment.repository.CommentJpaRepository;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.study.repository.StudyJpaRepository;
import song.teamo3.domain.studyapplication.entity.StudyApplication;
import song.teamo3.domain.studyapplication.repository.StudyApplicationJpaRepository;
import song.teamo3.domain.studymember.entity.StudyMember;
import song.teamo3.domain.studymember.entity.StudyMemberRole;
import song.teamo3.domain.studymember.repository.StudyMemberJpaRepository;
import song.teamo3.domain.user.entity.User;
import song.teamo3.domain.user.repository.UserJpaRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitConfig {
    private final InitService initService;

    @PostConstruct
    public void setInit() {
        initService.init();
    }

    @Component
    @RequiredArgsConstructor
    private static class InitService {
        private final PasswordEncoder passwordEncoder;
        private final UserJpaRepository userRepository;
        private final StudyJpaRepository studyRepository;
        private final StudyMemberJpaRepository studyMemberRepository;
        private final StudyApplicationJpaRepository studyApplicationRepository;
        private final CommentJpaRepository commentRepository;

        public void init() {
            User user1 = userRepository.save(User.create("1", passwordEncoder.encode("1"), "빌 게이츠"));
            User user2 = userRepository.save(User.create("2", passwordEncoder.encode("2"), "name 2"));
            User user3 = userRepository.save(User.create("3", passwordEncoder.encode("3"), "name 3"));

            Study study1 = studyRepository.save(Study.create(user1, "운영체제 스터디 모집합니다.", "<h4>운영체제 스터디 모집해요~</h4><p>운영체제 학습 및 개발 스터디 인원 모집합니다</p><ul><li>모집인원: 00명</li><li>스터디 일정: ~ 2100.01.01</li><li>참고사항: </li></ul>"));
            Study study2 = studyRepository.save(Study.create(user2, "t2", "d2"));

            StudyMember stMember1 = studyMemberRepository.save(StudyMember.create(user1, study1, StudyMemberRole.OWNER));
            StudyMember stMember2 = studyMemberRepository.save(StudyMember.create(user2, study2, StudyMemberRole.OWNER));
            StudyMember stMember3 = studyMemberRepository.save(StudyMember.create(user3, study2, StudyMemberRole.MEMBER));

            StudyApplication studyApplication1 = studyApplicationRepository.save(StudyApplication.create(user2, study1, "신청서 1", "<p>p1</p><p>p2</p><p>p3</p><p>p4</p><p>p5</p>"));
            StudyApplication studyApplication2 = studyApplicationRepository.save(StudyApplication.create(user3, study1, "신청서 2", "<p>p1</p><p>p2</p><p>p3</p><p>p4</p><p>p5</p><p>p6</p><p>p7</p><p>p8</p>"));

            Comment comment1 = commentRepository.save(Comment.create(user1, study1, "많은 응원 부탁드립니다."));
        }
    }
}
