package song.teamo3.domain.studymember.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import song.teamo3.domain.studymember.dto.StudyMemberPageDto;
import song.teamo3.domain.studymember.service.StudyMemberService;
import song.teamo3.security.authentication.userdetails.UserDetailsImpl;

@Slf4j
@Controller
@RequestMapping("/studyMember")
@RequiredArgsConstructor
public class StudyMemberController {
    private final StudyMemberService studyMemberService;

    @GetMapping("/")
    public String getStudyList(@AuthenticationPrincipal UserDetailsImpl userDetails,
                               @PageableDefault(size = 10, page = 0) Pageable pageable,
                               Model model) {
        Page<StudyMemberPageDto> studyMemberPage = studyMemberService.getStudyMemberPage(userDetails.getUser(), pageable);

        model.addAttribute("studyMemberPage", studyMemberPage);

        return "studymember/studyMemberList";
    }
}
