package song.teamo3.domain.common.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import song.teamo3.domain.chat.dto.ChatRoomListDto;
import song.teamo3.domain.chat.service.ChatRoomService;
import song.teamo3.domain.project.dto.ProjectPageDto;
import song.teamo3.domain.project.service.ProjectService;
import song.teamo3.domain.study.dto.StudyPageDto;
import song.teamo3.domain.study.service.StudyService;
import song.teamo3.security.authentication.userdetails.UserDetailsImpl;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    private final StudyService studyService;
    private final ProjectService projectService;
    private final ChatRoomService chatRoomService;

    @GetMapping
    public String getHome(@AuthenticationPrincipal UserDetailsImpl userDetails,
                          Model model) {
        PageRequest defaultPageable = PageRequest.of(0, 10);

        Page<ProjectPageDto> projectPage = projectService.getProjectPage(PageRequest.of(0, 6));
        model.addAttribute("projectPage", projectPage);

        Page<StudyPageDto> studyPage = studyService.getStudyPage(defaultPageable);
        model.addAttribute("studyPage", studyPage);

        Page<Object> empty = Page.empty();

        model.addAttribute("noticeList", empty);
        if (userDetails != null) {
            Page<ChatRoomListDto> chatRoomList = chatRoomService.getChatRoomList(userDetails.getUser());
            model.addAttribute("chatRoomList", chatRoomList);
        }

        return "home";
    }
}
