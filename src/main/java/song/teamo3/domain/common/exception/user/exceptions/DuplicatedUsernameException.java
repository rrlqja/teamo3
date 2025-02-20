package song.teamo3.domain.common.exception.user.exceptions;

import song.teamo3.domain.common.exception.user.UserException;

public class DuplicatedUsernameException extends UserException {
    public DuplicatedUsernameException() {
        super("Duplicated Username Exception");
    }

    public DuplicatedUsernameException(String message) {
        super(message);
    }
}
