package song.teamo3.domain.common.exception.user.exceptions;

import song.teamo3.domain.common.exception.user.UserException;

public class PasswordChangeException extends UserException {
    public PasswordChangeException() {
        super("Password Change Exception");
    }

    public PasswordChangeException(String message) {
        super(message);
    }
}
