package song.teamo3.domain.chat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
            "where cru.chatRoom = :chatRoom " +
            "  and cru.user = :user" +
            "  and cru.deleteFlag = false ")
    Optional<ChatRoomUser> findChatRoomUserByChatRoomAndUser(@Param("chatRoom") ChatRoom chatRoom,
                                                             @Param("user") User user);

    @Query("select cru.chatRoom cr" +
            " from ChatRoomUser cru " +
            "where cru.user = :user " +
            "  and cru.deleteFlag = false " +
            "order by cru.chatRoom.lastChatTime desc ")
    Page<ChatRoom> findChatRoomPageByUser(@Param("user") User user,
                                          Pageable pageable);

    @Query("select cru " +
            " from ChatRoomUser cru " +
            "where cru.deleteFlag = false " +
            "  and cru.chatRoom.id in :chatRoomIds")
    List<ChatRoomUser> findChatRoomUsersByChatRooms(@Param("chatRoomIds") List<Long> chatRoomIds);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update ChatRoomUser cru" +
            "  set cru.deleteFlag = true " +
            "where cru.chatRoom = :chatRoom")
    Integer deleteChatRoomByChatRoom(@Param("chatRoom") ChatRoom chatRoom);

    List<ChatRoomUser> findChatRoomUsersByChatRoom(ChatRoom chatRoom);
}
