package song.teamo3.domain.common.exception.comment.exceptions;

import song.teamo3.domain.common.exception.comment.CommentException;

public class CommentNotFoundException extends CommentException {
    public CommentNotFoundException() {
        super("Comment Not Found Exception");
    }

    public CommentNotFoundException(String message) {
        super(message);
    }
}
