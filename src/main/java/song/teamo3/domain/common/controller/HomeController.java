package song.teamo3.domain.common.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import song.teamo3.domain.study.dto.StudyPageDto;
import song.teamo3.domain.study.service.StudyService;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    private final StudyService studyService;

    @GetMapping
    public String getHome(Model model) {
        Page<StudyPageDto> studyPage = studyService.getStudyPage(PageRequest.of(0, 10));
        model.addAttribute("studyPage", studyPage);

        Page<Object> empty = Page.empty();
        model.addAttribute("teamingPage", empty);

        return "home";
    }
}
