package song.teamo3.domain.study.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import song.teamo3.domain.study.dto.CreateStudyDto;
import song.teamo3.domain.study.dto.EditStudyDto;
import song.teamo3.domain.study.dto.StudyDto;
import song.teamo3.domain.study.dto.StudyPageDto;
import song.teamo3.domain.study.service.StudyService;
import song.teamo3.security.authentication.userdetails.UserDetailsImpl;

@Slf4j
@Controller
@RequestMapping("/study")
@RequiredArgsConstructor
public class StudyController {
    private final StudyService studyService;

    @GetMapping("/studyList")
    public String getStudyList(@PageableDefault(size = 10, page = 0) Pageable pageable,
                               Model model) {
        Page<StudyPageDto> studyPage = studyService.getStudyPage(pageable);

        model.addAttribute("studyPage", studyPage);

        return "study/studyList";
    }

    @GetMapping("/create")
    public String getCreateStudy(@ModelAttribute(name = "study") CreateStudyDto createStudyDto) {

        return "study/createStudy";
    }

    @PostMapping("/create")
    public String postCreateStudy(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                  @ModelAttribute("study") CreateStudyDto createStudyDto,
                                  RedirectAttributes redirectAttributes) {
        Long studyId = studyService.createStudy(userDetails.getUser(), createStudyDto);

        redirectAttributes.addAttribute("studyId", studyId);

        return "redirect:/study/{studyId}";
    }

    @GetMapping("/{studyId}")
    public String getStudy(@AuthenticationPrincipal UserDetailsImpl userDetails,
                           @PathVariable("studyId") Long studyId,
                           Model model) {
        StudyDto study = null;
        if (userDetails == null) {
            study = studyService.getStudy(studyId);
        } else {
            study = studyService.getStudy(userDetails.getUser(), studyId);
        }

        model.addAttribute("study", study);

        return "study/study";
    }

    @GetMapping("/modify/{studyId}")
    public String getEditStudy(@AuthenticationPrincipal UserDetailsImpl userDetails,
                               @PathVariable("studyId") Long studyId,
                               Model model) {
        StudyDto study = studyService.getStudy(studyId);

        model.addAttribute("study", study);

        return "study/editStudy";
    }

    @PostMapping("/modify/{studyId}")
    public String postEditStudy(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                @PathVariable("studyId") Long studyId,
                                @ModelAttribute("study") EditStudyDto editStudyDto,
                                RedirectAttributes redirectAttributes) {
        Long editStudyId = studyService.editStudy(userDetails.getUser(), studyId, editStudyDto);

        redirectAttributes.addAttribute("studyId", editStudyId);

        return "redirect:/study/{studyId}";
    }
}
