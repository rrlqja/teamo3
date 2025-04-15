package song.teamo3.domain.studycalendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.studycalendar.entity.StudySchedule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudyScheduleJpaRepository extends JpaRepository<StudySchedule, Long> {
    @Query("select ss " +
            " from StudySchedule ss " +
            " join fetch ss.writer " +
            "where ss.id = :id " +
            "  and ss.deleteFlag = false")
    Optional<StudySchedule> findById(@Param("id") Long id);

    @Query("select ss " +
            " from StudySchedule ss " +
            "where ss.study in :studyList " +
            "  and ss.deleteFlag = false " +
            "order by ss.id asc, ss.startTime asc")
    List<StudySchedule> findStudySchedulesByStudyList(@Param("studyList") List<Study> studyList);

    @Query("select ss " +
            " from StudySchedule ss " +
            "where ss.study in :studyList " +
            "  and ss.startTime >= :start " +
            "  and ss.endTime <= :end " +
            "  and ss.deleteFlag = false " +
            "order by ss.id asc, ss.startTime desc")
    List<StudySchedule> findStudySchedulesByStudyList(@Param("studyList") List<Study> studyList,
                                                      @Param("start") LocalDateTime start,
                                                      @Param("end") LocalDateTime end);
}
