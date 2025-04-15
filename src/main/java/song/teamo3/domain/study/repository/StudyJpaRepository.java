package song.teamo3.domain.study.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
            " join fetch s.writer " +
            "where s.deleteFlag = false " +
            "  and s.title like concat('%', :title, '%') " +
            "order by s.bumpUpDate desc")
    Page<Study> findStudyPageByTitle(@Param("title") String title,
                                     Pageable pageable);

    @Query("select s " +
            " from Study s " +
            " join fetch s.writer " +
            "where s.deleteFlag = false " +
            "  and s.writer.name like concat('%', :writer, '%') " +
            "order by s.bumpUpDate desc")
    Page<Study> findStudyPageByWriter(@Param("writer") String writer,
                                     Pageable pageable);

    @Query("select s " +
            " from Study s " +
            " join fetch s.writer " +
            "where s.deleteFlag = false " +
            "  and s.content like concat('%', :content, '%') " +
            "order by s.bumpUpDate desc")
    Page<Study> findStudyPageByContent(@Param("content") String content,
                                      Pageable pageable);

    @Query("select s " +
            " from Study s " +
            " left outer join Favorite sf " +
            "   on s.id = sf.post.id " +
            " join fetch s.writer " +
            "where s.deleteFlag = false " +
            "  and s.createDate >= :createDate " +
            "  and sf.deleteFlag = false " +
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
