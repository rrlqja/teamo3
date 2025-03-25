package song.teamo3.domain.studyfavorite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.studyfavorite.entity.StudyFavorite;
import song.teamo3.domain.user.entity.User;

import java.util.Optional;

@Repository
public interface StudyFavoriteJpaRepository extends JpaRepository<StudyFavorite, Long> {
    @Query("select sf " +
            " from StudyFavorite sf " +
            " join fetch sf.study " +
            "where sf.user = :user " +
            "  and sf.study = :study" +
            "  and sf.deleteFlag = false")
    Optional<StudyFavorite> findStudyFavoriteByUserAndStudy(@Param("user") User user,
                                                            @Param("study") Study study);

    @Query("select count(sf) " +
            " from StudyFavorite sf " +
            "where sf.study = :study " +
            "  and sf.deleteFlag = false")
    Long findStudyFavoriteCountByStudy(@Param("study") Study study);
}
