package song.teamo3.domain.comment.dto;

import lombok.Data;
import song.teamo3.domain.comment.entity.Comment;

import java.time.format.DateTimeFormatter;

@Data
public class CommentPageDto {
    private Long id;
    private String writerUsername;
    private String writerName;
    private String text;
    private String createDate;
    private boolean deleted;

    public CommentPageDto(Comment comment) {
        this.id = comment.getId();
        this.writerUsername = comment.getWriter().getName();
        this.writerName = comment.getWriter().getName();
        this.text = comment.isDeleteFlag() ? "삭제된 댓글입니다." : comment.getText();
        this.createDate = comment.getCreateDate().format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss"));
        this.deleted = comment.isDeleteFlag();
    }
}
