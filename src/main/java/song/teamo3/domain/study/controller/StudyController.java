package song.teamo3.domain.study.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import song.teamo3.domain.study.dto.CreateStudyDto;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.study.service.StudyService;
import song.teamo3.security.authentication.userdetails.UserDetailsImpl;

import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/study")
@RequiredArgsConstructor
public class StudyController {
    private final StudyService studyService;

    @GetMapping("/studyList")
    public String getStudyList(@PageableDefault(size = 10, page = 0) Pageable pageable,
                               Model model) {
        Page<Study> studyPage = studyService.getStudyPage(pageable);

        model.addAttribute("studyPage", studyPage);

        return "study/studyList";
    }

    @GetMapping("/create")
    public String getCreateStudy(@ModelAttribute(name = "study") CreateStudyDto createStudyDto) {

        return "study/createStudy";
    }

    @ResponseBody
    @PostMapping("/create")
    public ResponseEntity<Object> postCreateStudy(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                  @RequestParam("editorContent") Object content) {
        log.info("content: {}, size: {}", content, content.getClass());
//        Long studyId = studyService.createStudy(userDetails.getUser(), createStudyDto);

        return ResponseEntity.ok(Map.of("studyId", 1L));
    }
}
