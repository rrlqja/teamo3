package song.teamo3.domain.common.exception.security;

import song.teamo3.domain.common.exception.TeamoException;

public class SecurityException extends TeamoException {
    public SecurityException() {
        super("Security Exception");
    }

    public SecurityException(String message) {
        super(message);
    }
}
