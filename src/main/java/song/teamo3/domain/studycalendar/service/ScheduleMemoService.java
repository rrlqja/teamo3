package song.teamo3.domain.studycalendar.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo3.domain.common.exception.schedulememo.exceptions.ScheduleMemoAccessNotAllowedException;
import song.teamo3.domain.common.exception.schedulememo.exceptions.ScheduleMemoNotFoundException;
import song.teamo3.domain.common.exception.studyschedule.exceptions.StudyScheduleNotFoundException;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.studycalendar.dto.CreateScheduleMemoDto;
import song.teamo3.domain.studycalendar.dto.ModifyScheduleMemoDto;
import song.teamo3.domain.studycalendar.dto.ScheduleMemoDto;
import song.teamo3.domain.studycalendar.dto.ScheduleMemoPageDto;
import song.teamo3.domain.studycalendar.entity.ScheduleMemo;
import song.teamo3.domain.studycalendar.entity.StudySchedule;
import song.teamo3.domain.studycalendar.repository.ScheduleMemoJpaRepository;
import song.teamo3.domain.studycalendar.repository.StudyScheduleJpaRepository;
import song.teamo3.domain.studymember.service.StudyMemberService;
import song.teamo3.domain.user.entity.User;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleMemoService {
    private final ScheduleMemoJpaRepository scheduleMemoRepository;
    private final StudyScheduleJpaRepository studyScheduleRepository;
    private final StudyMemberService studyMemberService;

    @Transactional
    public Page<ScheduleMemoPageDto> getScheduleMemo(User user, Long studyScheduleId, Pageable pageable) {
        StudySchedule studySchedule = getStudySchedule(studyScheduleId);

        isMember(user, studySchedule);

        return scheduleMemoRepository.findScheduleMemosByStudySchedule(studySchedule, pageable)
                .map(sm -> {
                    return new ScheduleMemoPageDto(sm, user);
                });

    }

    @Transactional
    public ScheduleMemoDto createMemo(User user, Long scheduleId, CreateScheduleMemoDto createScheduleMemoDto) {
        StudySchedule studySchedule = getStudySchedule(scheduleId);

        isMember(user, studySchedule);

        ScheduleMemo scheduleMemo = createScheduleMemoDto.toEntity(user, studySchedule);
        ScheduleMemo saveScheduleMemo = scheduleMemoRepository.save(scheduleMemo);

        return new ScheduleMemoDto(saveScheduleMemo);
    }

    @Transactional
    public ScheduleMemoDto modifyScheduleMemo(User user, Long scheduleMemoId, ModifyScheduleMemoDto modifyScheduleMemoDto) {
        ScheduleMemo scheduleMemo = scheduleMemoRepository.findById(scheduleMemoId)
                .orElseThrow(ScheduleMemoNotFoundException::new);

        if (!scheduleMemo.getWriter().getId().equals(user.getId())) {
            throw new ScheduleMemoAccessNotAllowedException("잘못된 요청입니다.");
        }

        scheduleMemo.modify(modifyScheduleMemoDto.getDescription());
        ScheduleMemo modifiedMemo = scheduleMemoRepository.save(scheduleMemo);

        return new ScheduleMemoDto(modifiedMemo);
    }

    @Transactional
    public void deleteMemo(User user, Long scheduleMemoId) {
        ScheduleMemo scheduleMemo = scheduleMemoRepository.findById(scheduleMemoId)
                .orElseThrow(ScheduleMemoNotFoundException::new);

        if (!scheduleMemo.getWriter().getId().equals(user.getId())) {
            throw new ScheduleMemoAccessNotAllowedException("잘못된 요청입니다.");
        }

        scheduleMemo.delete();
    }

    private void isMember(User user, StudySchedule studySchedule) {
        Study study = studySchedule.getStudy();
        if (!studyMemberService.isMember(user, study)) {
            throw new ScheduleMemoAccessNotAllowedException("잘못된 요청입니다.");
        }
    }

    private StudySchedule getStudySchedule(Long scheduleId) {
        return studyScheduleRepository.findById(scheduleId)
                .orElseThrow(StudyScheduleNotFoundException::new);
    }
}
