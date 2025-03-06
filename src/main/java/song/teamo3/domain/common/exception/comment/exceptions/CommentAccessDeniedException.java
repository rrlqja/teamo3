package song.teamo3.domain.common.exception.comment.exceptions;

import song.teamo3.domain.common.exception.comment.CommentException;

public class CommentAccessDeniedException extends CommentException {
    public CommentAccessDeniedException() {
        super("Comment Access Denied Exception");
    }

    public CommentAccessDeniedException(String message) {
        super(message);
    }
}
