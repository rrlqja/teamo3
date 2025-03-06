package song.teamo3.domain.comment.dto;

import lombok.Data;
import song.teamo3.domain.comment.entity.Comment;
import song.teamo3.domain.study.entity.Study;
import song.teamo3.domain.user.entity.User;

@Data
public class CreateCommentDto {
    private String text;

    public Comment toEntity(User writer, Study study) {
        return Comment.create(writer, study, text);
    }
}
