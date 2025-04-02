package song.teamo3.domain.comment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.teamo3.domain.comment.entity.Comment;
import song.teamo3.domain.post.entity.Post;

import java.util.Optional;

@Repository
public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
    @Query("select c " +
            " from Comment c " +
            " join fetch c.writer " +
            "where c.post = :post")
    Page<Comment> findCommentsByStudy(@Param("post") Post post,
                                      Pageable pageable);

    @Query("select c " +
            " from Comment c " +
            " join fetch c.writer " +
            " join fetch c.post " +
            "where c.id = :id")
    Optional<Comment> findCommentById(@Param("id") Long id);
}
