package song.teamo3.domain.chat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.teamo3.domain.chat.entity.Chat;
import song.teamo3.domain.chat.entity.ChatRoom;

@Repository
public interface ChatJpaRepository extends JpaRepository<Chat, Long> {
    @Query("select c " +
            " from Chat c " +
            " join fetch c.writer " +
            "where c.chatRoom = :chatRoom " +
            "order by c.createDate desc")
    Page<Chat> findChatsByChatRoom(@Param("chatRoom") ChatRoom chatRoom,
                                   Pageable pageable);
}
