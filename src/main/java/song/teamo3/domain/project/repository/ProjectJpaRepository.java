package song.teamo3.domain.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.teamo3.domain.project.entity.Project;

import java.util.Optional;

@Repository
public interface ProjectJpaRepository extends JpaRepository<Project, Long> {
    @Query("select p " +
            " from Project p " +
            " join fetch p.writer " +
            "where p.deleteFlag = false " +
            "order by p.createDate desc")
    Page<Project> findAll(Pageable pageable);

    @Query("select p " +
            " from Project p " +
            " join fetch p.writer " +
            "where p.deleteFlag = false " +
            "  and p.title like concat('%', :title, '%') " +
            "order by p.createDate desc")
    Page<Project> findProjectPageByTitle(@Param("title") String title,
                                         Pageable pageable);

    @Query("select p " +
            " from Project p " +
            " join fetch p.writer " +
            "where p.deleteFlag = false " +
            "  and p.writer.name like concat('%', :writer, '%') " +
            "order by p.createDate desc")
    Page<Project> findProjectPageByWriter(@Param("writer") String writer,
                                          Pageable pageable);

    @Query("select p " +
            " from Project p " +
            " join fetch p.writer " +
            "where p.deleteFlag = false " +
            "  and p.content like concat('%', :content, '%') " +
            "order by p.createDate desc")
    Page<Project> findProjectPageByContent(@Param("content") String content,
                                           Pageable pageable);

    @Query("select p " +
            " from Project p " +
            " left join fetch p.imgList " +
            "where p.id = :id")
    Optional<Project> findProjectById(@Param("id") Long id);
}
