package song.teamo3.domain.studyapplication.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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
}
