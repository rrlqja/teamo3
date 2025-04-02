package song.teamo3.domain.common.exception.post;

import song.teamo3.domain.common.exception.TeamoException;

public class PostException extends TeamoException {
    public PostException() {
        super("Post Exception");
    }

    public PostException(String message) {
        super(message);
    }
}
