package song.teamo3.domain.studycalendar.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo3.domain.common.exception.study.exceptions.StudyNotFoundException;
import song.teamo3.domain.common.exception.studyschedule.exceptions.StudyScheduleAccessDeniedException;
import song.teamo3.domain.common.exception.studyschedule.exceptions.StudyScheduleNotFoundException;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.study.repository.StudyJpaRepository;
import song.teamo3.domain.studycalendar.dto.CreateStudyScheduleDto;
import song.teamo3.domain.studycalendar.dto.ModifyStudyScheduleDto;
import song.teamo3.domain.studycalendar.dto.StudyScheduleDto;
import song.teamo3.domain.studycalendar.dto.StudyScheduleListDto;
import song.teamo3.domain.studycalendar.entity.StudySchedule;
import song.teamo3.domain.studycalendar.repository.StudyScheduleJpaRepository;
import song.teamo3.domain.studymember.service.StudyMemberService;
import song.teamo3.domain.user.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudyScheduleService {
    private final StudyMemberService studyMemberService;
    private final StudyJpaRepository studyRepository;
    private final StudyScheduleJpaRepository studyScheduleRepository;

    @Transactional
    public List<StudyScheduleListDto> getStudySchedules(User user, Long studyId, String start, String end, String date) {
        List<Study> studyList = studyMemberService.getStudyListByUser(user);

        if (studyId != null) {
            studyList = studyList.stream()
                    .filter(study -> study.getId().equals(studyId))
                    .collect(Collectors.toList());
        }

        List<StudySchedule> schedules;
        if (start != null && end != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime startDateTime = LocalDateTime.parse(start.substring(0, 19), formatter);
            LocalDateTime endDateTime = LocalDateTime.parse(end.substring(0, 19), formatter);
            schedules = studyScheduleRepository.findStudySchedulesByStudyList(studyList, startDateTime, endDateTime);
        } else {
            schedules = studyScheduleRepository.findStudySchedulesByStudyList(studyList);
        }

        if (date != null) {
            LocalDate targetDate = LocalDate.parse(date);
            schedules = schedules.stream()
                    .filter(schedule -> {
                        LocalDate startDate = schedule.getStartTime().toLocalDate();
                        LocalDate endDate = schedule.getEndTime().toLocalDate();
                        return !targetDate.isBefore(startDate) && !targetDate.isAfter(endDate);
                    })
                    .toList();
        }

        return schedules.stream()
                .map(studySchedule -> {
                    return new StudyScheduleListDto(studySchedule, user);})
                .toList();
    }

    @Transactional
    public void addStudySchedule(User user, CreateStudyScheduleDto createStudyScheduleDto) {
        Study study = studyRepository.findStudyById(createStudyScheduleDto.getStudyId())
                .orElseThrow(StudyNotFoundException::new);

        if (!studyMemberService.isMember(user, study)) {
            throw new StudyScheduleAccessDeniedException("잘못된 요청입니다.");
        }

        StudySchedule studySchedule = createStudyScheduleDto.toEntity(user, study);

        StudySchedule saveStudySchedule = studyScheduleRepository.save(studySchedule);
    }

    @Transactional
    public StudyScheduleDto modifyStudySchedule(User user, Long scheduleId, ModifyStudyScheduleDto modifyStudyScheduleDto) {
        StudySchedule studySchedule = getStudySchedule(scheduleId);

        if (!studySchedule.getWriter().getId().equals(user.getId())) {
            throw new StudyScheduleAccessDeniedException("수정할 수 없습니다.");
        }

        studySchedule.modify(modifyStudyScheduleDto.getTitle(), modifyStudyScheduleDto.getDescription(), modifyStudyScheduleDto.getStart(), modifyStudyScheduleDto.getEnd());

        StudySchedule modifiedStudySchedule = studyScheduleRepository.save(studySchedule);

        return new StudyScheduleDto(modifiedStudySchedule, user);
    }

    @Transactional
    public void deleteStudySchedule(User user, Long scheduleId) {
        StudySchedule studySchedule = getStudySchedule(scheduleId);

        if (!studySchedule.getWriter().getId().equals(user.getId())) {
            throw new StudyScheduleAccessDeniedException("잘못된 요청입니다.");
        }

        studySchedule.delete();
        studyScheduleRepository.save(studySchedule);
    }

    private StudySchedule getStudySchedule(Long scheduleId) {
        return studyScheduleRepository.findById(scheduleId)
                .orElseThrow(StudyScheduleNotFoundException::new);
    }
}
