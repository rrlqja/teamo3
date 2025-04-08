package song.teamo3.domain.favorite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.teamo3.domain.post.entity.Post;
import song.teamo3.domain.favorite.entity.Favorite;
import song.teamo3.domain.user.entity.User;

import java.util.Optional;

@Repository
public interface FavoriteJpaRepository extends JpaRepository<Favorite, Long> {
    @Query("select f " +
            " from Favorite f " +
            " join fetch f.post " +
            "where f.user = :user " +
            "  and f.post = :post")
    Optional<Favorite> findFavoriteByUserAndPost(@Param("user") User user,
                                                 @Param("post") Post post);

    @Query("select count(f) " +
            " from Favorite f " +
            "where f.post = :post " +
            "  and f.deleteFlag = false")
    Long findFavoriteCountByPost(@Param("post") Post post);
}
