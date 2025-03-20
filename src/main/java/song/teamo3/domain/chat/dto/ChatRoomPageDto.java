package song.teamo3.domain.chat.dto;

import lombok.Data;
import song.teamo3.domain.chat.entity.ChatRoom;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChatRoomPageDto {
    private Long id;
    private String studyName;
    private List<ChatRoomUserListDto> userList = new ArrayList<>();

    public ChatRoomPageDto(ChatRoom chatRoom, List<ChatRoomUserListDto> chatRoomUserList) {
        this.id = chatRoom.getId();
        this.studyName = chatRoom.getStudy().getTitle();
        this.userList = chatRoomUserList;
    }
}
