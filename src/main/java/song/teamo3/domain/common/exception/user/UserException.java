package song.teamo3.domain.common.exception.user;

import song.teamo3.domain.common.exception.TeamoException;

public class UserException extends TeamoException {
    public UserException() {
        super("User Exception");
    }

    public UserException(String message) {
        super(message);
    }
}
