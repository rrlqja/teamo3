package song.teamo3.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
        private final ChatRoomUserJpaRepository chatRoomUserJpaRepository;
        private final ChatService chatService;
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

        public void init() {
            User user1 = userRepository.save(User.create("1", passwordEncoder.encode("1"), "관리자"));
            User user2 = userRepository.save(User.create("2", passwordEncoder.encode("2"), "포롬"));
            User user3 = userRepository.save(User.create("3", passwordEncoder.encode("3"), "라리밖"));
            User user4 = userRepository.save(User.create("4", passwordEncoder.encode("4"), "name 3"));

            Study study1 = studyRepository.save(Study.create(user2, "액션 게임 제작 스터디원 모집합니다!", "<h4>게임 제작 스터디원 모집합니다!</h4><p>보스 토벌 액션 RPG를 개발하고 싶어요! 같이 참여해보세요!</p><ul><li>모집인원: 00명</li><li>스터디 일정: ~ 2100.01.01</li><li>참고사항: </li></ul>", "액션 게임 제작 스터디"));
            Study study2 = studyRepository.save(Study.create(user3, "게임 제작 스터디 구해요~", "<h4>게임 제작 스터디 구해요!</h4><p>오픈월드 턴제 RPG를 개발하고 싶어요! 같이 참여해보세요!</p><ul><li>모집인원: 00명</li><li>스터디 일정: ~ 2100.01.01</li><li>참고사항: </li></ul>", "오픈월드 게임 제작 스터디"));

            for (int i = 0; i < 30; i++) {
                Study study = studyRepository.save(Study.create(user2, "조용한 스터디 " + i, "스터디 모집 " + i, "개인 스터디 " + i));
                StudyMember studyMember = studyMemberRepository.save(StudyMember.create(user2, study, StudyMemberRole.ADMIN));
            }

            StudyMember stMember1 = studyMemberRepository.save(StudyMember.create(user2, study1, StudyMemberRole.OWNER));
            StudyMember stMember2 = studyMemberRepository.save(StudyMember.create(user3, study2, StudyMemberRole.OWNER));
            StudyMember stMember3 = studyMemberRepository.save(StudyMember.create(user4, study2, StudyMemberRole.MEMBER));

            StudyApplication studyApplication1 = studyApplicationRepository.save(StudyApplication.create(user3, study1, "신청서 1", "<p>p1</p><p>p2</p><p>p3</p><p>p4</p><p>p5</p>"));
            StudyApplication studyApplication2 = studyApplicationRepository.save(StudyApplication.create(user4, study1, "신청서 2", "<p>p1</p><p>p2</p><p>p3</p><p>p4</p><p>p5</p><p>p6</p><p>p7</p><p>p8</p>"));

            Comment comment1 = commentRepository.save(Comment.create(user2, study1, "많은 응원 부탁드립니다."));

            Project project1 = projectRepository.save(createProject(user2, study1, "엘든링을 소개합니다!", "<p>게임 제작 스터디를 통해</p><p>엘든링이라는 액션 RPG 게임을 만들었습니다!</p><p>많은 관심 부탁드립니다!</p><p>&nbsp;</p><figure class=\"image\"><img style=\"aspect-ratio:854/480;\" src=\"" + downloadPath + "er1.gif\" width=\"854\" height=\"480\"></figure><p>&nbsp;</p><figure class=\"image\"><img style=\"aspect-ratio:854/480;\" src=\"" + downloadPath + "er2.gif\" width=\"854\" height=\"480\"><p>&nbsp;</p></figure><figure class=\"image\"><img style=\"aspect-ratio:854/480;\" src=\"" + downloadPath + "er3.gif\" width=\"854\" height=\"480\"></figure>", List.of(downloadPath +"er.webp"), "https://namu.wiki/w/%EC%97%98%EB%93%A0%20%EB%A7%81", "subTitle"));
            List<ProjectMember> projectMember1List = projectMemberRepository.saveAll(ProjectMember.create(List.of(user2), project1));

            Project project2 = projectRepository.save(createProject(user3, study2, "발더스 게이트를 소개합니다!", "<p>게임 제작 스터디를 통해</p><p>발더스 게이트라는 오픈월드 턴제 RPG 게임을 만들었습니다!</p><p>많은 관심 부탁드립니다!</p><p>&nbsp;</p><figure class=\"image\"><img style=\"aspect-ratio:854/480;\" src=\"" + downloadPath + "bg1.jpg\" width=\"854\" height=\"480\"></figure><p>동료들을 모아 적들과 전투를 할 수 있어요!</p><p>&nbsp;</p><figure class=\"image\"><img style=\"aspect-ratio:854/480;\" src=\"" + downloadPath + "bg2.png\" width=\"854\" height=\"480\"><p>&nbsp;</p></figure><p>동료들과의 로맨스도 즐겨보세요!</p>", List.of(downloadPath + "bg.webp"), "https://namu.wiki/w/%EB%B0%9C%EB%8D%94%EC%8A%A4%20%EA%B2%8C%EC%9D%B4%ED%8A%B8%203", "subTitle"));
            List<ProjectMember> projectMember2List = projectMemberRepository.saveAll(ProjectMember.create(List.of(user3, user4), project2));

            Project project3 = projectRepository.save(createProject(user3, study2, "발더스 게이트를 소개합니다!", "<p>게임 제작 스터디를 통해</p><p>발더스 게이트라는 오픈월드 턴제 RPG 게임을 만들었습니다!</p><p>많은 관심 부탁드립니다!</p><p>&nbsp;</p><figure class=\"image\"><img style=\"aspect-ratio:854/480;\" src=\"" + downloadPath + "bg1.jpg\" width=\"854\" height=\"480\"></figure><p>동료들을 모아 적들과 전투를 할 수 있어요!</p><p>&nbsp;</p><figure class=\"image\"><img style=\"aspect-ratio:854/480;\" src=\"" + downloadPath + "bg2.png\" width=\"854\" height=\"480\"><p>&nbsp;</p></figure><p>동료들과의 로맨스도 즐겨보세요!</p>", List.of(downloadPath + "bg.webp"), "https://namu.wiki/w/%EB%B0%9C%EB%8D%94%EC%8A%A4%20%EA%B2%8C%EC%9D%B4%ED%8A%B8%203", "subTitle"));
            List<ProjectMember> projectMember3List = projectMemberRepository.saveAll(ProjectMember.create(List.of(user3, user4), project3));

            Project project4 = projectRepository.save(createProject(user3, study2, "발더스 게이트를 소개합니다!", "<p>게임 제작 스터디를 통해</p><p>발더스 게이트라는 오픈월드 턴제 RPG 게임을 만들었습니다!</p><p>많은 관심 부탁드립니다!</p><p>&nbsp;</p><figure class=\"image\"><img style=\"aspect-ratio:854/480;\" src=\"" + downloadPath + "bg1.jpg\" width=\"854\" height=\"480\"></figure><p>동료들을 모아 적들과 전투를 할 수 있어요!</p><p>&nbsp;</p><figure class=\"image\"><img style=\"aspect-ratio:854/480;\" src=\"" + downloadPath + "bg2.png\" width=\"854\" height=\"480\"><p>&nbsp;</p></figure><p>동료들과의 로맨스도 즐겨보세요!</p>", List.of(downloadPath + "Teamo.png"), "https://namu.wiki/w/%EB%B0%9C%EB%8D%94%EC%8A%A4%20%EA%B2%8C%EC%9D%B4%ED%8A%B8%203", "subTitle"));
            List<ProjectMember> projectMember4List = projectMemberRepository.saveAll(ProjectMember.create(List.of(user3, user4), project4));

            Long chatRoom1Id = chatRoomService.createChatRoom(study1);
            ChatRoom chatRoom1 = chatRoomRepository.findChatRoomById(chatRoom1Id).get();

            for (int i = 0; i < 20; i++) {
                Study study = studyRepository.findStudyById(i + 2L).get();
                ChatRoom chatRoom = ChatRoom.create(study);
                ChatRoom scr = chatRoomRepository.save(chatRoom);
                ChatRoomUser cru = ChatRoomUser.create(scr, user2);
                ChatRoomUser scru = chatRoomUserJpaRepository.save(cru);
            }

            for (int i = 0; i < 20; i++) {
                chatService.saveChat(user2, chatRoom1.getId(), "테스트 메시지 " + i);
            }

            createStudySchedule(study1, user2, 5, 0L, 0L);
            createStudySchedule(study1, user2, 2, 1L, 1L);
            createStudySchedule(study1, user2, 1, -1L, -1L);
            createStudySchedule(study1, user2, 2, 3L, 5L);
            createStudySchedule(study1, user2, 1, 40L, 41L);

            createScheduleMemo(user2, 1L, 14);
        }

        private void createScheduleMemo(User user2, Long studyScheduleId, int count) {
            for (int i = 0; i < count; i++) {
                scheduleMemoRepository.save(ScheduleMemo.create(user2, studyScheduleRepository.findById(studyScheduleId).get(), "스케줄 메모 " + i));
            }
        }

        private void createStudySchedule(Study study, User user, int count, Long startDate, Long endDate) {
            for (int i = 0; i < count; i++) {
                studyScheduleRepository.save(StudySchedule.create(study, user, "오프라인 회의" + i, "스터디 오프라인 회의 진행", getNow().plusDays(startDate), getNow().plusDays(endDate)));
            }
        }

        private static LocalDateTime getNow() {
            return LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        }

        private static Project createProject(User user, Study study, String title, String descrption, List<String> imgList, String url, String subTitle) {
            return Project.create(user, study, title, descrption, imgList, url, subTitle);
        }
    }
}
