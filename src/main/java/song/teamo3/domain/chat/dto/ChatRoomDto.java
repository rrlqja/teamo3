package song.teamo3.domain.chat.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import song.teamo3.domain.chat.entity.ChatRoom;
import song.teamo3.domain.user.entity.User;

import java.time.format.DateTimeFormatter;

@Data
public class ChatRoomDto {
    private Long id;
    private String chatRoomName;
    private String lastChatTime;
    private boolean isAdmin = false;
    private Page<ChatPageDto> chatPage = Page.empty();

    public ChatRoomDto(ChatRoom chatRoom, User user, Page<ChatPageDto> chatPage) {
        this.id = chatRoom.getId();
        this.chatRoomName = chatRoom.getChatRoomName();
        this.lastChatTime = chatRoom.getLastChatTime() != null ? chatRoom.getLastChatTime().format(DateTimeFormatter.ofPattern("yy.MM.dd HH:mm")) : null;
        this.isAdmin = chatRoom.getStudy().getWriter().getId().equals(user.getId());
        this.chatPage = chatPage;
    }
}
