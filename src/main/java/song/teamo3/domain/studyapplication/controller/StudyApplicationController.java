package song.teamo3.domain.studyapplication.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import song.teamo3.domain.studyapplication.dto.StudyApplicationDto;
import song.teamo3.domain.studyapplication.service.StudyApplicationService;
import song.teamo3.security.authentication.userdetails.UserDetailsImpl;

@Slf4j
@Controller
@RequestMapping("/studyApplication")
@RequiredArgsConstructor
public class StudyApplicationController {
    private final StudyApplicationService studyApplicationService;

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{studyApplicationId}")
    public StudyApplicationDto getStudyApplication(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                   @PathVariable("studyApplicationId") Long studyApplicationId) {
        StudyApplicationDto studyApplication = studyApplicationService.getStudyApplication(userDetails.getUser(), studyApplicationId);

        return studyApplication;
    }

    @PostMapping("/approve/{studyApplicationId}")
    public String postApprove(@AuthenticationPrincipal UserDetailsImpl userDetails,
                              @PathVariable("studyApplicationId") Long studyApplicationId,
                              RedirectAttributes redirectAttributes) {
        Long studyId = studyApplicationService.approve(userDetails.getUser(), studyApplicationId);

        redirectAttributes.addAttribute("studyId", studyId);

        return "redirect:/study/{studyId}/applicationList";
    }

    @PostMapping("/reject/{studyApplicationId}")
    public String postReject(@AuthenticationPrincipal UserDetailsImpl userDetails,
                             @PathVariable("studyApplicationId") Long studyApplicationId,
                             RedirectAttributes redirectAttributes) {
        Long studyId = studyApplicationService.reject(userDetails.getUser(), studyApplicationId);

        redirectAttributes.addAttribute("studyId", studyId);

        return "redirect:/study/{studyId}/applicationList";
    }
}
