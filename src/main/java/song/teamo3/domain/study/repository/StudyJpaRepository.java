package song.teamo3.domain.study.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.teamo3.domain.study.entity.Study;

import java.util.Optional;

@Repository
public interface StudyJpaRepository extends JpaRepository<Study, Long> {
    @Query("select s " +
            " from Study s " +
            "where s.deleteFlag = false ")
    Page<Study> findStudyPage(Pageable pageable);

    @Query("select s " +
            " from Study s " +
            " join fetch s.writer " +
            "where s.id = :id ")
    Optional<Study> findStudyById(@Param("id") Long id);
}
