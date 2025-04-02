package song.teamo3.domain.common.exception.post.exceptions;

import song.teamo3.domain.common.exception.post.PostException;

public class PostNotFoundException extends PostException {
    public PostNotFoundException() {
        super("Post Not Found Exception");
    }

    public PostNotFoundException(String message) {
        super(message);
    }
}
