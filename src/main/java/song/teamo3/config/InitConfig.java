package song.teamo3.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import song.teamo3.domain.chat.entity.Chat;
import song.teamo3.domain.chat.entity.ChatRoom;
import song.teamo3.domain.chat.entity.ChatRoomUser;
import song.teamo3.domain.chat.repository.ChatJpaRepository;
import song.teamo3.domain.chat.repository.ChatRoomJpaRepository;
import song.teamo3.domain.chat.repository.ChatRoomUserJpaRepository;
import song.teamo3.domain.chat.service.ChatRoomService;
import song.teamo3.domain.chat.service.ChatService;
import song.teamo3.domain.comment.entity.Comment;
import song.teamo3.domain.comment.repository.CommentJpaRepository;
import song.teamo3.domain.project.entity.Project;
import song.teamo3.domain.project.entity.ProjectMember;
import song.teamo3.domain.project.repository.ProjectJpaRepository;
import song.teamo3.domain.project.repository.ProjectMemberJpaRepository;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.study.repository.StudyJpaRepository;
import song.teamo3.domain.studyapplication.entity.StudyApplication;
import song.teamo3.domain.studyapplication.repository.StudyApplicationJpaRepository;
import song.teamo3.domain.studycalendar.entity.ScheduleMemo;
import song.teamo3.domain.studycalendar.entity.StudySchedule;
import song.teamo3.domain.studycalendar.repository.ScheduleMemoJpaRepository;
import song.teamo3.domain.studycalendar.repository.StudyScheduleJpaRepository;
import song.teamo3.domain.studymember.entity.StudyMember;
import song.teamo3.domain.studymember.entity.StudyMemberRole;
import song.teamo3.domain.studymember.repository.StudyMemberJpaRepository;
import song.teamo3.domain.user.entity.User;
import song.teamo3.domain.user.repository.UserJpaRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@Profile("!dev")
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
        @Value("${download.path}")
        private String downloadPath;

        private final PasswordEncoder passwordEncoder;
        private final UserJpaRepository userRepository;
        private final StudyJpaRepository studyRepository;
        private final StudyMemberJpaRepository studyMemberRepository;
        private final StudyApplicationJpaRepository studyApplicationRepository;
        private final CommentJpaRepository commentRepository;
        private final ProjectJpaRepository projectRepository;
        private final ProjectMemberJpaRepository projectMemberRepository;
        private final ChatRoomService chatRoomService;
        private final ChatRoomJpaRepository chatRoomRepository;
        private final ChatJpaRepository chatRepository;
        private final StudyScheduleJpaRepository studyScheduleRepository;
        private final ScheduleMemoJpaRepository scheduleMemoRepository;
        private final ChatRoomUserJpaRepository chatRoomUserRepository;
        private final ChatService chatService;

        public void init() {
            for (int i = 0; i < 20; i++) {
                userRepository.save(User.create("id"+i, passwordEncoder.encode(String.valueOf(i)), "테스트 사용자 " + i));
            }

            User admin1 = userRepository.save(User.create("admin1", passwordEncoder.encode("1"), "관리자1"));
            User admin2 = userRepository.save(User.create("admin2", passwordEncoder.encode("3"), "관리자2"));
            User admin3 = userRepository.save(User.create("admin3", passwordEncoder.encode("3"), "관리자3"));

            User user1 = userRepository.save(User.create("1", passwordEncoder.encode("1"), "공부하는 원숭이"));
            User user2 = userRepository.save(User.create("2", passwordEncoder.encode("2"), "포롬소프트"));
            User user3 = userRepository.save(User.create("3", passwordEncoder.encode("3"), "라리안 스튜디오"));
            User user4 = userRepository.save(User.create("4", passwordEncoder.encode("4"), "유네소프트"));

            for (int i = 0; i < 20; i++) {
                Study study = studyRepository.save(Study.create(user1, "테스트 스터디 " + i, "테스트 스터디 모집글입니다.", "우당탕탕 스터디 " + i));
                StudyMember studyMemberAdmin = studyMemberRepository.save(StudyMember.create(user1, study, StudyMemberRole.ADMIN));
                StudyMember studyMemberMember = studyMemberRepository.save(StudyMember.create(userRepository.findById(i + 1L).get(), study, StudyMemberRole.MEMBER));
            }

            Study study1 = studyRepository.save(Study.create(user2, "액션 게임 제작 스터디원 모집합니다!", "<h4>게임 제작 스터디원 모집합니다!</h4><p>보스 토벌 액션 RPG를 개발하고 싶어요! 같이 참여해보세요!</p><ul><li>모집인원: 00명</li><li>스터디 일정: ~ 2100.01.01</li><li>참고사항: </li></ul>", "액션 게임 제작 스터디"));
            Study study2 = studyRepository.save(Study.create(user3, "게임 제작 스터디 구해요~", "<h4>게임 제작 스터디 구해요!</h4><p>오픈월드 턴제 RPG를 개발하고 싶어요! 같이 참여해보세요!</p><ul><li>모집인원: 00명</li><li>스터디 일정: ~ 2100.01.01</li><li>참고사항: </li></ul>", "오픈월드 게임 제작 스터디"));

            StudyMember stMember1 = studyMemberRepository.save(StudyMember.create(user2, study1, StudyMemberRole.OWNER));
            StudyMember stMember2 = studyMemberRepository.save(StudyMember.create(user3, study2, StudyMemberRole.OWNER));
            StudyMember stMember3 = studyMemberRepository.save(StudyMember.create(user4, study2, StudyMemberRole.MEMBER));

            for (int i = 0; i < 20; i++) {
                User userI = userRepository.findById(i + 1L).get();
                for (int j = 0; j < 20; j++) {
                    Study studyJ = studyRepository.findById(j + 1L).get();
                    studyApplicationRepository.save(StudyApplication.create(userI, studyJ, "테스트 신청서 " + (i + 1), "<p>테스트 가입 신청서입니다.</p><p>테스트 가입 신청서입니다.</p><p>테스트 가입 신청서입니다.</p>"));
                }
            }

            StudyApplication studyApplication1 = studyApplicationRepository.save(StudyApplication.create(user3, study1, "신청서 1", "<p>p1</p><p>p2</p><p>p3</p><p>p4</p><p>p5</p>"));
            StudyApplication studyApplication2 = studyApplicationRepository.save(StudyApplication.create(user4, study1, "신청서 2", "<p>p1</p><p>p2</p><p>p3</p><p>p4</p><p>p5</p><p>p6</p><p>p7</p><p>p8</p>"));

            for (int i = 0; i < 20; i++) {
                Study studyI = studyRepository.findById(i + 1L).get();
                commentRepository.save(Comment.create(user2, studyI, "테스트 댓글입니다."));
                for (int j = 0; j < 8; j++) {
                    User userJ = userRepository.findById(j + 1L).get();
                    commentRepository.save(Comment.create(userJ, studyI, "테스트 댓글입니다."));
                }
            }

            for (int i = 0; i < 3; i++) {
                Project projectI = projectRepository.save(createProject(user2, study1, "테스트 프로젝트", "<p>테스트 프로젝트입니다.</p><p>테스트 프로젝트입니다.</p><p>&nbsp;</p><figure class=\"image\"><img style=\"aspect-ratio:854/480;\" src=\"" + downloadPath + "proContent" + (i + 1) + ".jpg\" width=\"854\" height=\"480\">", List.of(downloadPath + "proThumb" + (i + 1) + ".jpg"), "https://www.google.co.kr/", "부제"));
                List<ProjectMember> projectMember1List = projectMemberRepository.saveAll(ProjectMember.create(List.of(user2), projectI));
            }

            for (int i = 0; i < 20; i++) {
                Study studyI = studyRepository.findById(i + 1L).get();
                Long chatRoomIId = chatRoomService.createChatRoom(studyI);

                ChatRoom chatRoomI = chatRoomRepository.findById(chatRoomIId).get();
                for (int j = 0; j < 10; j++) {
                    chatService.saveChat(user1, chatRoomIId, "테스트 메시지입니다. " + j);
                    chatService.saveChat(userRepository.findById(i + 1L).get(), chatRoomIId, "테스트 메시지입니다. " + j);
                }
            }

            for (int i = 0; i < 10; i++) {
                Study studyI = studyRepository.findById(i + 1L).get();
                createStudySchedule(studyI, user1, i, (long) i, (long) i);

                if (i == 0) {
                    createStudySchedule(studyI, user1, i, (long) i, (long) i + 3);
                }

                if (i == 5) {
                    createStudySchedule(studyI, user1, i, -13L, -11L);
                }

                if (i == 9) {
                    createStudySchedule(studyI, user1, i, i + 10L, i + 20L);
                }

                for (int j = 0; j < 3; j++) {
                    createScheduleMemo(user1, (long) i + 1, j);
                }
            }
        }

        private void createScheduleMemo(User user, Long studyScheduleId, int number) {
            scheduleMemoRepository.save(ScheduleMemo.create(user, studyScheduleRepository.findById(studyScheduleId).get(), "스케줄 메모 " + number));
        }

        private void createStudySchedule(Study study, User user, int number, Long startDate, Long endDate) {
            studyScheduleRepository.save(StudySchedule.create(study, user, "오프라인 회의 " + number, "스터디 오프라인 회의 진행", getNow().plusDays(startDate), getNow().plusDays(endDate)));
        }

        private static LocalDateTime getNow() {
            return LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        }

        private static Project createProject(User user, Study study, String title, String descrption, List<String> imgList, String url, String subTitle) {
            return Project.create(user, study, title, descrption, imgList, url, subTitle);
        }
    }
}
