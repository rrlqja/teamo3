package song.teamo3.domain.chat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.teamo3.domain.chat.entity.Chat;
import song.teamo3.domain.chat.entity.ChatRoom;

import java.util.List;

@Repository
public interface ChatJpaRepository extends JpaRepository<Chat, Long> {
    @Query("select c " +
            " from Chat c " +
            " join fetch c.writer " +
            "where c.chatRoom = :chatRoom " +
            "order by c.createDate desc")
    Page<Chat> findChatsByChatRoom(@Param("chatRoom") ChatRoom chatRoom,
                                   Pageable pageable);

    @Query("select c from Chat c " +
            "where c.chatRoom.id in :chatRoomIds and c.createDate in (" +
            "      select max(c2.createDate) from Chat c2 where c2.chatRoom.id = c.chatRoom.id" +
            "      )")
    List<Chat> findLastChatsByChatRoomIds(@Param("chatRoomIds") List<Long> chatRoomIds);
}
