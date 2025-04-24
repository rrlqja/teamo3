package song.teamo3.domain.studycalendar.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import song.teamo3.domain.study.dto.StudyPageDto;
import song.teamo3.domain.study.service.StudyService;
import song.teamo3.domain.studycalendar.dto.CreateScheduleMemoDto;
import song.teamo3.domain.studycalendar.dto.CreateStudyScheduleDto;
import song.teamo3.domain.studycalendar.dto.ModifyScheduleMemoDto;
import song.teamo3.domain.studycalendar.dto.ModifyStudyScheduleDto;
import song.teamo3.domain.studycalendar.dto.ScheduleMemoDto;
import song.teamo3.domain.studycalendar.dto.ScheduleMemoPageDto;
import song.teamo3.domain.studycalendar.dto.StudyScheduleDto;
import song.teamo3.domain.studycalendar.dto.StudyScheduleListDto;
import song.teamo3.domain.studycalendar.service.ScheduleMemoService;
import song.teamo3.domain.studycalendar.service.StudyScheduleService;
import song.teamo3.security.authentication.userdetails.UserDetailsImpl;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/studyCalendar")
@RequiredArgsConstructor
public class StudyCalendarController {
    private final StudyScheduleService studyScheduleService;
    private final ScheduleMemoService scheduleMemoService;
    private final StudyService studyService;

    @GetMapping("/")
    public String getStudyCalendar(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                   Model model) {
        Page<StudyPageDto> studyPage = studyService.getStudyByUser(userDetails.getUser(), PageRequest.of(0, 10));
        model.addAttribute("studyPage", studyPage);

        return "studycalendar/studyCalendar";
    }

    @GetMapping("/studySchedules")
    public ResponseEntity<List<StudyScheduleListDto>> getStudySchedules(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                        @RequestParam(name = "studyId", required = false) Long studyId,
                                                                        @RequestParam(name = "start", required = false) String start,
                                                                        @RequestParam(name = "end", required = false) String end,
                                                                        @RequestParam(name = "date", required = false) String date) {
        List<StudyScheduleListDto> studyScheduleList = studyScheduleService.getStudySchedules(userDetails.getUser(), studyId, start, end, date);

        return ResponseEntity.ok(studyScheduleList);
    }

    @PostMapping("/addStudySchedule")
    public ResponseEntity<Void> postAddStudySchedule(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @RequestBody CreateStudyScheduleDto createStudyScheduleDto) {
        studyScheduleService.addStudySchedule(userDetails.getUser(), createStudyScheduleDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/studyList")
    public ResponseEntity<Page<StudyPageDto>> getStudyPage(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                           @PageableDefault(size = 10, page = 0) Pageable pageable) {
        Page<StudyPageDto> studyPage = studyService.getStudyByUser(userDetails.getUser(), pageable);

        return ResponseEntity.ok(studyPage);
    }

    @PostMapping("/studySchedule/modify/{scheduleId}")
    public ResponseEntity<StudyScheduleDto> postModifyStudySchedule(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                    @PathVariable(name = "scheduleId") Long scheduleId,
                                                                    @RequestBody ModifyStudyScheduleDto modifyStudyScheduleDto) {
        StudyScheduleDto studySchedule = studyScheduleService.modifyStudySchedule(userDetails.getUser(), scheduleId, modifyStudyScheduleDto);

        return ResponseEntity.ok(studySchedule);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/studySchedule/{studyScheduleId}")
    public void deleteStudySchedule(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @PathVariable(name = "studyScheduleId") Long scheduleId) {
        studyScheduleService.deleteStudySchedule(userDetails.getUser(), scheduleId);
    }

    @GetMapping("/{scheduleId}/memoList")
    public ResponseEntity<Page<ScheduleMemoPageDto>> getScheduleMemoPage(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                         @PathVariable(name = "scheduleId") Long scheduleId,
                                                                         @PageableDefault(size = 10, page = 0) Pageable pageable) {
        Page<ScheduleMemoPageDto> scheduleMemoPage = scheduleMemoService.getScheduleMemo(userDetails.getUser(), scheduleId, pageable);

        return ResponseEntity.ok(scheduleMemoPage);
    }

    @PostMapping("/{scheduleId}/memo")
    public ResponseEntity<ScheduleMemoDto> postAddScheduleMemo(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                               @PathVariable(name = "scheduleId") Long scheduleId,
                                                               @RequestBody CreateScheduleMemoDto createScheduleMemoDto) {
        ScheduleMemoDto memo = scheduleMemoService.createMemo(userDetails.getUser(), scheduleId, createScheduleMemoDto);

        return ResponseEntity.ok(memo);
    }

    @PutMapping("/memo/modify/{scheduleMemoId}")
    public ResponseEntity<ScheduleMemoDto> postModifyScheduleMemo(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                  @PathVariable(name = "scheduleMemoId") Long scheduleMemoId,
                                                                  @RequestBody ModifyScheduleMemoDto modifyScheduleMemoDto) {
        ScheduleMemoDto memo = scheduleMemoService.modifyScheduleMemo(userDetails.getUser(), scheduleMemoId, modifyScheduleMemoDto);

        return ResponseEntity.ok(memo);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/memo/delete/{scheduleMemoId}")
    public void deleteScheduleMemo(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                   @PathVariable(name = "scheduleMemoId") Long scheduleMemoId) {
        scheduleMemoService.deleteMemo(userDetails.getUser(), scheduleMemoId);
    }
}
