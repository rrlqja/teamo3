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
import song.teamo3.domain.favorite.service.FavoriteService;
import song.teamo3.domain.project.dto.CreateProjectDto;
import song.teamo3.domain.project.dto.CreateProjectMemberListDto;
import song.teamo3.domain.project.entity.Project;
import song.teamo3.domain.project.entity.ProjectMember;
import song.teamo3.domain.project.repository.ProjectJpaRepository;
import song.teamo3.domain.project.repository.ProjectMemberJpaRepository;
import song.teamo3.domain.project.service.ProjectService;
import song.teamo3.domain.study.dto.CreateStudyDto;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.study.repository.StudyJpaRepository;
import song.teamo3.domain.study.service.StudyService;
import song.teamo3.domain.studyapplication.entity.StudyApplication;
import song.teamo3.domain.studyapplication.repository.StudyApplicationJpaRepository;
import song.teamo3.domain.studycalendar.entity.ScheduleMemo;
import song.teamo3.domain.studycalendar.entity.StudySchedule;
import song.teamo3.domain.studycalendar.repository.ScheduleMemoJpaRepository;
import song.teamo3.domain.studycalendar.repository.StudyScheduleJpaRepository;
import song.teamo3.domain.studymember.dto.StudyMemberListDto;
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
        private final ProjectService projectService;
        private final StudyService studyService;
        @Value("${download.path}")
        private String downloadPath;

        private final PasswordEncoder passwordEncoder;
        private final UserJpaRepository userRepository;
        private final StudyJpaRepository studyRepository;
        private final StudyMemberJpaRepository studyMemberRepository;
        private final StudyApplicationJpaRepository studyApplicationRepository;
        private final FavoriteService favoriteService;
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

            User user1 = userRepository.save(User.create("admin99", passwordEncoder.encode("admin99"), "자라나는 강낭콩"));
            User user2 = userRepository.save(User.create("2", passwordEncoder.encode("2"), "포롬소프트"));
            User user3 = userRepository.save(User.create("3", passwordEncoder.encode("3"), "라리밖 스튜디오"));
            User user4 = userRepository.save(User.create("4", passwordEncoder.encode("4"), "유네소프트"));

            for (int i = 0; i < 20; i++) {
                if (i == 19) {
                    Study study = studyRepository.save(Study.create(user1, "모각코 모집합니다!", "<p>각자 공부하는 모각코 모집합니다!</p><ul><li>모집인원: 00명</li><li>스터디 일정: 상시</li><li>참고사항: &nbsp;귀여운 사람만 가입할 수 있어요!!</li><li>지역: 내 마음속</li></ul>", "나는귀여워"));
                    StudyMember studyMemberAdmin = studyMemberRepository.save(StudyMember.create(user1, study, StudyMemberRole.ADMIN));
                    StudyMember studyMemberMember = studyMemberRepository.save(StudyMember.create(userRepository.findById(i + 1L).get(), study, StudyMemberRole.MEMBER));
                    break;
                }
                Study study = studyRepository.save(Study.create(user1, "테스트 스터디 " + i, "테스트 스터디 모집글입니다.", "우당탕탕 스터디 " + i));
                StudyMember studyMemberAdmin = studyMemberRepository.save(StudyMember.create(user1, study, StudyMemberRole.ADMIN));
                StudyMember studyMemberMember = studyMemberRepository.save(StudyMember.create(userRepository.findById(i + 1L).get(), study, StudyMemberRole.MEMBER));
            }

            Study study1 = studyRepository.save(Study.create(user2, "액션 게임 제작 스터디원 모집합니다!", "<h4>게임 제작 스터디원 모집합니다!</h4><p>보스 토벌 액션 RPG를 개발하고 싶어요! 같이 참여해보세요!</p><ul><li>모집인원: 00명</li><li>스터디 일정: ~ 2100.01.01</li><li>참고사항: </li></ul>", "액션 게임 제작 스터디"));
            Study study2 = studyRepository.save(Study.create(user3, "게임 제작 스터디 구해요~", "<h4>게임 제작 스터디 구해요!</h4><p>오픈월드 턴제 RPG를 개발하고 싶어요! 같이 참여해보세요!</p><ul><li>모집인원: 00명</li><li>스터디 일정: ~ 2100.01.01</li><li>참고사항: </li></ul>", "오픈월드 게임 제작 스터디"));

            for (int i = 0; i < 20; i++) {
                User userI = userRepository.findById(i + 1L).get();
                favoriteService.favorite(userI, 22L);

                if (i > 5) {
                    favoriteService.favorite(userI, 21L);
                }

                if (i > 12) {
                    favoriteService.favorite(userI, 20L);
                }
            }

            StudyMember stMember1 = studyMemberRepository.save(StudyMember.create(user2, study1, StudyMemberRole.OWNER));
            StudyMember stMember2 = studyMemberRepository.save(StudyMember.create(user3, study2, StudyMemberRole.OWNER));
            StudyMember stMember3 = studyMemberRepository.save(StudyMember.create(user4, study2, StudyMemberRole.MEMBER));

            for (int i = 0; i < 20; i++) {
                User userI = userRepository.findById(i + 1L).get();
                for (int j = 0; j < 20; j++) {
                    if (i == 19 && j == 19) {
                        Study studyJ = studyRepository.findById(j + 1L).get();
                        studyApplicationRepository.save(StudyApplication.create(userI, studyJ, "나는 원한다 스터디 가입", "<p>나는 세상에서 제일 귀엽다</p><p>그러므로 너는 나를 뽑아야 한다</p><p>나는 딸기도 좋아하고, 케이크도 좋아하고, 딸기 케이크도 좋아한다</p>"));
                        break;
                    }
                    Study studyJ = studyRepository.findById(j + 1L).get();
                    studyApplicationRepository.save(StudyApplication.create(userI, studyJ, "테스트 신청서 " + (i + 1), "<p>테스트 가입 신청서입니다.</p><p>테스트 가입 신청서입니다.</p><p>테스트 가입 신청서입니다.</p>"));
                }
            }

            StudyApplication studyApplication1 = studyApplicationRepository.save(StudyApplication.create(user3, study1, "신청서 1", "<p>p1</p><p>p2</p><p>p3</p><p>p4</p><p>p5</p>"));
            StudyApplication studyApplication2 = studyApplicationRepository.save(StudyApplication.create(user4, study1, "신청서 2", "<p>p1</p><p>p2</p><p>p3</p><p>p4</p><p>p5</p><p>p6</p><p>p7</p><p>p8</p>"));

            for (int i = 0; i < 20; i++) {
                Study studyI = studyRepository.findById(i + 1L).get();
                commentRepository.save(Comment.create(user1, studyI, "테스트 댓글입니다."));
                for (int j = 0; j < 8; j++) {
                    User userJ = userRepository.findById(j + 1L).get();
                    commentRepository.save(Comment.create(userJ, studyI, "테스트 댓글입니다."));
                }
            }

            for (int i = 0; i < 3; i++) {
                Study studyI = studyRepository.findById(i + 1L).get();
                Project projectI = projectRepository.save(createProject(user1, studyI, "테스트 프로젝트", "<p>테스트 프로젝트입니다.</p><p>테스트 프로젝트입니다.</p><p>&nbsp;</p><figure class=\"image\"><img style=\"aspect-ratio:854/480;\" src=\"" + downloadPath + "proContent" + (i + 1) + ".jpg\" width=\"854\" height=\"480\">", List.of(downloadPath + "proThumb" + (i + 1) + ".jpg"), "https://www.google.co.kr/", "부제"));
                List<ProjectMember> projectMember1List = projectMemberRepository.saveAll(ProjectMember.create(List.of(user1), projectI));
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

            createTeamoProject(user1);
        }

        private void createTeamoProject(User user) {
            Long teamId = studyService.createStudy(user, new CreateStudyDto("웹 애플리케이션 스터디 모집합니다.", "<span>스터디 모집 웹 애플리케이션 개발을 위한 스터디 모집합니다.</span><br/><ul><li>모집인원: 00명</li><li>스터디 일정: ~ 2100.01.01</li><li>참고사항: </li></ul><span>많관부</span>", "티모"));
            Study teamoStudy = studyRepository.findById(teamId).get();

            CreateProjectDto projectDto = new CreateProjectDto();
            projectDto.setStudyId(teamoStudy.getId());
            projectDto.setTitle("스터디 모집 커뮤니티 - 티모");
            projectDto.setSubTitle("스터디를 모집해보아요");
            projectDto.setDescription("<p>귀여운 사람들끼리 모여 스터디 모집 커뮤니티를 만들어보았어요!!</p><p>부족한 점이 많지만 귀엽게봐주세요!!</p><p>&nbsp;</p><figure class=\"image\"><img style=\"aspect-ratio:259/194;\" src=\"" + downloadPath + "catb.jpg\" width=\"259\" height=\"194\"></figure><p>&nbsp;</p><p>&nbsp;</p><p><strong>- 홈페이지</strong></p><figure class=\"image\"><img style=\"aspect-ratio:2207/1846;\" src=\"" + downloadPath + "t1.png\" width=\"2207\" height=\"1846\"></figure><p>첫 페이지 접속 시 모집중인 스터디, 홍보중인 프로젝트들을 확인 할 수 있어요!</p><p>&nbsp;</p><p><strong>- 스터디 모집</strong></p><figure class=\"image\"><img style=\"aspect-ratio:965/797;\" src=\"" + downloadPath + "t2-1.png\" width=\"965\" height=\"797\"></figure><p>스터디 목록으로 가볼까요?!</p><p>&nbsp;</p><figure class=\"image\"><img style=\"aspect-ratio:1354/1765;\" src=\"" + downloadPath + "t2-2.png\" width=\"1354\" height=\"1765\"></figure><p>모집중이거나 모집완료된 스터디 모집글들을 확인할 수 있어요!&nbsp;</p><p>직접 스터디를 생성하고 싶다면 하단에 스터디 생성 버튼을 통해 새로운 스터디를 생성할 수 있어요.</p><p>&nbsp;</p><figure class=\"image image_resized\" style=\"width:78.45%;\"><img style=\"aspect-ratio:1529/1223;\" src=\"" + downloadPath + "t2-3.png\" width=\"1529\" height=\"1223\"></figure><p>&nbsp;</p><p>직접 만든 스터디에 가입을 원하는 다른 사용자들이 있어요!&nbsp;</p><figure class=\"image\"><img style=\"aspect-ratio:1753/1774;\" src=\"" + downloadPath + "t2-4.png\" width=\"1753\" height=\"1774\"></figure><figure class=\"image\"><img style=\"aspect-ratio:1984/1685;\" src=\"" + downloadPath + "t2-5.png\" width=\"1984\" height=\"1685\"></figure><p>&nbsp;</p><p>스터디 가입 신청서를 확인하고, 가입을 승인 및 거절하여 스터디원을 추가할 수 있어요!</p><p>&nbsp;</p><p><strong>- 실시간 채팅</strong></p><p>개설한 스터디에 스터디원이 생겼다면 서로 채팅을 통해 스터디와 관련된 이야기를 자유롭게 나눌 수 있어요</p><figure class=\"image\"><img style=\"aspect-ratio:2144/1529;\" src=\"" + downloadPath + "t2-6.png\" width=\"2144\" height=\"1529\"></figure><p>&nbsp;</p><p>- 스터디 캘린더</p><p>서로 일정을 조율하고 대화하면서 스터디간의 일정도 공유 할 수 있답니다!</p><figure class=\"image\"><img style=\"aspect-ratio:2486/1873;\" src=\"" + downloadPath + "t2-7.png\" width=\"2486\" height=\"1873\"></figure><p>&nbsp;</p><p>&nbsp;</p><p><strong>- 프로젝트 홍보</strong></p><p>스터디를 통해 스터디원과 프로젝트 제작까지 이루어졌다면 만든 프로젝트를 다른 사용자들에게 홍보해볼 수도 있어요!</p><figure class=\"image\"><img style=\"aspect-ratio:2242/1886;\" src=\"" + downloadPath + "t2-8.png\" width=\"2242\" height=\"1886\"></figure><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>** 테스트 계정을 통해 `스터디 모집 커뮤니티 - 티모` 를 직접 체험해보세요! **</p><p>&nbsp;</p>");
            projectDto.setUrl("https://www.google.co.kr/");
            projectDto.setImgList(List.of(downloadPath + "teamo.png"));

            Long teamoProjectId = projectService.create(user, projectDto);
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
