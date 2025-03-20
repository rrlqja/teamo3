package song.teamo3.domain.chat.dto;

import lombok.Data;
import song.teamo3.domain.chat.entity.Chat;

import java.time.format.DateTimeFormatter;

@Data
public class ChatPageDto {
    private Long id;
    private String message;
    private String createDate;

    private Long writerId;
    private String writerName;

    public ChatPageDto(Chat chat) {
        this.id = chat.getId();
        this.message = chat.getMessage();
        this.createDate = chat.getCreateDate().format(DateTimeFormatter.ofPattern("yy.MM.dd HH:mm"));
        this.writerId = chat.getWriter().getId();
        this.writerName = chat.getWriter().getName();
    }
}
