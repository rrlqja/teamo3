package song.teamo3.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.teamo3.domain.post.entity.Post;

import java.util.Optional;

@Repository
public interface PostJpaRepository extends JpaRepository<Post, Long> {
    @Query("select p " +
            " from Post p " +
            "where p.id = :id")
    Optional<Post> findPostById(@Param("id") Long id);
}
