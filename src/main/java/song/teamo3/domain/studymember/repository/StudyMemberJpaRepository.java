package song.teamo3.domain.studymember.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.studymember.entity.StudyMember;
import song.teamo3.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyMemberJpaRepository extends JpaRepository<StudyMember, Long> {
    @Query("select sm " +
            " from StudyMember sm " +
            "where sm.user = :user " +
            "  and sm.study = :study " +
            "  and sm.deleteFlag = false")
    Optional<StudyMember> findStudyMemberByUserAndStudy(@Param("user") User user,
                                                        @Param("study") Study study);

    @Query("select sm " +
            " from StudyMember sm " +
            " join fetch sm.user " +
            " join fetch sm.study sms " +
            " join fetch sms.writer " +
            "where sm.user = :user " +
            "  and sms.deleteFlag = false")
    Page<StudyMember> findStudyMembersByUser(@Param("user") User user,
                                             Pageable pageable);

    @Query("select sm " +
            " from StudyMember sm " +
            " join fetch sm.user " +
            "where sm.study = :study" +
            "  and sm.deleteFlag = false")
    List<StudyMember> findStudyMembersByStudy(@Param("study") Study study);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update StudyMember sm" +
            "  set sm.deleteFlag = true " +
            "where sm.study = :study ")
    Integer deleteStudyMembersByStudy(@Param("study") Study study);
}
