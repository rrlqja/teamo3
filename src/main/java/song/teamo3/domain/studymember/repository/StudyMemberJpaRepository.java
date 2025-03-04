package song.teamo3.domain.studymember.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.studymember.entity.StudyMember;
import song.teamo3.domain.user.entity.User;

import java.util.Optional;

@Repository
public interface StudyMemberJpaRepository extends JpaRepository<StudyMember, Long> {
    @Query("select sm " +
            " from StudyMember sm " +
            "where sm.user = :user " +
            "  and sm.study = :study ")
    Optional<StudyMember> findStudyMemberByUserAndStudy(@Param("user") User user,
                                                        @Param("study") Study study);

    @Query("select sm " +
            " from StudyMember sm " +
            " join fetch sm.user " +
            " join fetch sm.study " +
            "where sm.user = :user ")
    Page<StudyMember> findStudyMembersByUser(@Param("user") User user,
                                             Pageable pageable);
}
