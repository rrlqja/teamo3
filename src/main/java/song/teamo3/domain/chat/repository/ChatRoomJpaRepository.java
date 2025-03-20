package song.teamo3.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.teamo3.domain.chat.entity.ChatRoom;
import song.teamo3.domain.study.entity.Study;

import java.util.Optional;

@Repository
public interface ChatRoomJpaRepository extends JpaRepository<ChatRoom, Long> {
    @Query("select cr " +
            " from ChatRoom cr " +
            "where cr.study = :study")
    Optional<ChatRoom> findChatRoomByStudy(@Param("study") Study study);

    @Query("select cr " +
            " from ChatRoom cr " +
            "where cr.id = :id")
    Optional<ChatRoom> findChatRoomById(@Param("id") Long id);
}
