package song.teamo3.domain.studycalendar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.teamo3.domain.studycalendar.entity.ScheduleMemo;
import song.teamo3.domain.studycalendar.entity.StudySchedule;

import java.util.Optional;

@Repository
public interface ScheduleMemoJpaRepository extends JpaRepository<ScheduleMemo, Long> {
    @Query("select sm " +
            " from ScheduleMemo  sm " +
            "where sm.studySchedule = :studySchedule " +
            "  and sm.deleteFlag = false")
    Page<ScheduleMemo> findScheduleMemosByStudySchedule(@Param("studySchedule") StudySchedule studySchedule, Pageable pageable);

    @Query("select sm " +
            " from ScheduleMemo sm " +
            " join fetch sm.writer " +
            "where sm.id = :id " +
            "  and sm.deleteFlag = false")
    Optional<ScheduleMemo> findById(@Param("id") Long id);
}
