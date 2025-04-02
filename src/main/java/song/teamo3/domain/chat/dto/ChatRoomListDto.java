package song.teamo3.domain.chat.dto;

import lombok.Data;
import song.teamo3.domain.chat.entity.Chat;
import song.teamo3.domain.chat.entity.ChatRoom;

@Data
public class ChatRoomListDto {
    private Long id;
    private String chatRoomName;
    private String writerName;
    private String message;

    public ChatRoomListDto(ChatRoom chatRoom, Chat chat) {
        this.id = chatRoom.getId();
        this.chatRoomName = chatRoom.getChatRoomName();
        if (chat != null) {
            this.writerName = chat.getWriter().getName();
            this.message = chat.getMessage();
        }
    }
}
