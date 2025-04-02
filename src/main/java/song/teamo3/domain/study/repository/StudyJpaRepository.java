package song.teamo3.domain.study.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.teamo3.domain.study.entity.Study;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface StudyJpaRepository extends JpaRepository<Study, Long> {
    @Query("select s " +
            " from Study s " +
            " join fetch s.writer " +
            "where s.deleteFlag = false " +
            "order by s.bumpUpDate desc")
    Page<Study> findStudyPage(Pageable pageable);

    @Query("select s " +
            " from Study s " +
            " left outer join Favorite sf " +
            "   on s.id = sf.post.id " +
            " join fetch s.writer " +
            "where s.deleteFlag = false " +
            "  and s.createDate >= :createDate " +
            "group by s " +
            "order by count(sf) desc, s.createDate desc")
    Page<Study> findBestStudyPage(@Param("createDate") LocalDateTime createDate,
                                  Pageable pageable);

    @Query("select s " +
            " from Study s " +
            " join fetch s.writer " +
            "where s.id = :id ")
    Optional<Study> findStudyById(@Param("id") Long id);
}
