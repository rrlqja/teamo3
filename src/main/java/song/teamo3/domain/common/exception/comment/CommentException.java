package song.teamo3.domain.common.exception.comment;

import song.teamo3.domain.common.exception.TeamoException;

public class CommentException extends TeamoException {
    public CommentException() {
        super("Comment Exception");
    }

    public CommentException(String message) {
        super(message);
    }
}
