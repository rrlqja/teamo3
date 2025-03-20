package song.teamo3.domain.chat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.teamo3.domain.chat.entity.ChatRoom;
import song.teamo3.domain.chat.entity.ChatRoomUser;
import song.teamo3.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomUserJpaRepository extends JpaRepository<ChatRoomUser, Long> {
    @Query("select cru " +
            " from ChatRoomUser cru " +
            " join fetch cru.chatRoom " +
            "where cru.user = :user " +
            "  and cru.deleted = false ")
    Page<ChatRoomUser> findChatRoomUsersByUser(@Param("user") User user, Pageable pageable);

    @Query("select cru " +
            " from ChatRoomUser cru " +
            " join fetch cru.user " +
            "where cru.chatRoom = :chatRoom " +
            "  and cru.deleted = false ")
    List<ChatRoomUser> findChatRoomUsersByChatRoom(@Param("chatRoom") ChatRoom chatRoom);

    @Query("select cru " +
            " from ChatRoomUser cru " +
            "where cru.chatRoom = :chatRoom " +
            "  and cru.user = :user" +
            "  and cru.deleted = false ")
    Optional<ChatRoomUser> findChatRoomUserByChatRoomAndUser(@Param("chatRoom") ChatRoom chatRoom,
                                                             @Param("user") User user);
}
