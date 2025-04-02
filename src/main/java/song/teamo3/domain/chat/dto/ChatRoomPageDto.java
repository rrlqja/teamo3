package song.teamo3.domain.chat.dto;

import lombok.Data;
import song.teamo3.domain.chat.entity.Chat;
import song.teamo3.domain.chat.entity.ChatRoom;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChatRoomPageDto {
    private Long id;
    private String chatRoomName;
    private List<ChatRoomUserListDto> userList = new ArrayList<>();
    private String lastChatWriterName;
    private String lastChat;

    public ChatRoomPageDto(ChatRoom chatRoom, List<ChatRoomUserListDto> chatRoomUserList, Chat chat) {
        this.id = chatRoom.getId();
        this.chatRoomName = chatRoom.getChatRoomName();
        this.userList = chatRoomUserList;

        if (chat != null) {
            this.lastChatWriterName = chat.getWriter().getName();
            this.lastChat = chat.getMessage();
        }
    }
}
