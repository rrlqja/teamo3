package song.teamo3.domain.studyapplication.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.studyapplication.entity.StudyApplication;
import song.teamo3.domain.studyapplication.entity.StudyApplicationStatus;
import song.teamo3.domain.user.entity.User;

import java.util.Optional;

@Repository
public interface StudyApplicationJpaRepository extends JpaRepository<StudyApplication, Long> {
    @Query("select sa " +
            " from StudyApplication sa " +
            "where sa.user = :user " +
            "  and sa.study = :study " +
            "  and sa.status = :status")
    Optional<StudyApplication> findStudyApplicationByUserAndStudy(@Param("user") User user,
                                                                  @Param("study") Study study,
                                                                  @Param("status") StudyApplicationStatus status);

    @Query("select sa " +
            " from StudyApplication sa " +
            "where sa.study = :study " +
            "  and sa.status = :status")
    Page<StudyApplication> findPendingStudyApplicationsByStudy(@Param("study") Study study,
                                                               @Param("status") StudyApplicationStatus status,
                                                               Pageable pageable);

    @Query("select sa " +
            " from StudyApplication sa " +
            " join fetch sa.user " +
            " join fetch sa.study " +
            "where sa.id = :id")
    Optional<StudyApplication> findById(@Param("id") Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete " +
            " from StudyApplication sa " +
            "where sa.study = :study")
    Integer deleteStudyApplicationsByStudy(@Param("study") Study study);
}
