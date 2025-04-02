package song.teamo3.domain.comment.dto;

import lombok.Data;
import song.teamo3.domain.comment.entity.Comment;
import song.teamo3.domain.post.entity.Post;
import song.teamo3.domain.user.entity.User;

@Data
public class CreateCommentDto {
    private String text;

    public Comment toEntity(User writer, Post post) {
        return Comment.create(writer, post, text);
    }
}
