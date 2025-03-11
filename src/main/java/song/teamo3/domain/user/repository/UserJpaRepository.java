package song.teamo3.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.teamo3.domain.user.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
    @Query("select u " +
            " from User u " +
            "where u.username = :username ")
    Optional<User> findUserByUsername(@Param("username") String username);

    @Query("select u " +
            " from User u " +
            "where u.id in :ids")
    List<User> findUsersByIdIn(@Param("ids") Collection<Long> ids);
}
