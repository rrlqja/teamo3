package song.teamo3.domain.study.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import song.teamo3.domain.study.entity.Study;

@Repository
public interface StudyJpaRepository extends JpaRepository<Study, Long> {
    @Query("select s " +
            " from Study s ")
    Page<Study> findStudyPage(Pageable pageable);
}
