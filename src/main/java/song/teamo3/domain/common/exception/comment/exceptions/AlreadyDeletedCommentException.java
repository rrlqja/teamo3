package song.teamo3.domain.common.exception.comment.exceptions;

import song.teamo3.domain.common.exception.comment.CommentException;

public class AlreadyDeletedCommentException extends CommentException {
    public AlreadyDeletedCommentException() {
        super("Already Deleted Comment Exception");
    }

    public AlreadyDeletedCommentException(String message) {
        super(message);
    }
}
