package song.teamo3.domain.chat.dto;

import lombok.Data;
import song.teamo3.domain.chat.entity.ChatRoomUser;

@Data
public class ChatRoomUserListDto {
    private Long userId;
    private String name;

    public ChatRoomUserListDto(ChatRoomUser chatRoomUser) {
        this.userId = chatRoomUser.getUser().getId();
        this.name = chatRoomUser.getUser().getName();
    }
}
